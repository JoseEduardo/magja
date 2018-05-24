package net.magja.soap;

import com.google.common.base.Preconditions;
import net.magja.magento.ResourcePath;
import org.apache.axiom.om.OMElement;
import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.xml.namespace.QName;
import java.util.*;

/**
 * Magento SOAP Client.
 *
 * @author unknown
 * @author Marco Lopes (marcolopes@netc.pt)
 * @author Simon Zambrovski
 */
public class MagentoSoapClient implements SoapClient {
  private static final Logger log = LoggerFactory.getLogger(MagentoSoapClient.class);
  private static final QName LOGIN_RETURN = new QName("loginReturn");
  private static final QName LOGOUT_RETURN = new QName("endSessionReturn");
  private static final QName CALL_RETURN = new QName("callReturn");

  private SoapCallFactory callFactory;
  private SoapReturnParser returnParser;
  private SoapConfig config;

  private String sessionId;
  private ServiceClient sender;

  // holds all the created instances by creation order, Multiton Pattern
  private static final Map<SoapConfig, SoapClient> INSTANCES = new LinkedHashMap<SoapConfig, SoapClient>();

  private static Optional<Map.Entry<SoapConfig, SoapClient>> getByConfiguration(Configuration configuration) {
    return INSTANCES.entrySet().stream()
      .filter(x -> x.getKey().getIdConfiguration().equals(configuration.getId()))
      .findFirst();
  }

  /**
   * Returns the default instance, or a newly created one from the
   * magento-api.properties file, if there is no default instance. The default
   * instance is the first one created.
   *
   * @return the default instance or a newly created one
   */
  public static SoapClient getInstance(Configuration configuration) {

    Optional<Map.Entry<SoapConfig, SoapClient>> soapStorage = getByConfiguration(configuration);

    SoapClient soapClient = !soapStorage.isPresent() ? getInstance(configuration, null) : soapStorage.get().getValue();
    if (soapClient.getConfig().getIdConfiguration().equals(configuration.getId())) {
      return soapClient;
    }
    return null;
  }

  /**
   * Returns the instance that was created with the specified configuration or
   * create one if the instance does not exist.
   *
   * @return the already created instance or a new one
   */
  public static SoapClient getInstance(Configuration configuration, final SoapConfig soapConfig) {
    // if has default instance and soapConfig is null
    Optional<Map.Entry<SoapConfig, SoapClient>> soapStorage = getByConfiguration(configuration);
    if (soapStorage.isPresent()) {
      return soapStorage.get().getValue();
    }

    synchronized (INSTANCES) {
      SoapConfig loadedSoapConfig = null;
      if (soapConfig == null) {
        loadedSoapConfig = new SoapConfig(configuration);
        if (loadedSoapConfig == null) { // still null?
          throw new RuntimeException("Cannot create soapConfig");
        }
      } else {
        loadedSoapConfig = soapConfig;
      }

      SoapClient instance = INSTANCES.get(loadedSoapConfig);
      if (instance == null) {
        instance = new MagentoSoapClient(loadedSoapConfig);
        INSTANCES.put(loadedSoapConfig, instance);
      }
      return instance;
    }
  }

  /**
   * Construct soap client using given configuration
   *
   * @param soapConfig
   */
  public MagentoSoapClient(final SoapConfig soapConfig, final SoapCallFactory callFactory) {
    Preconditions.checkNotNull(soapConfig, "soapConfig cannot be null");
    Preconditions.checkNotNull(callFactory, "callFactory cannot be null");
    this.config = soapConfig;
    this.callFactory = callFactory;
    this.returnParser = new SoapReturnParser();
    try {
      prepareConnection(config.getHttpConnectionManagerParams());
      login();
    } catch (AxisFault e) {
      // do not swallow, rethrow as runtime
      throw new RuntimeException("Cannot connect to Magento", e);
    }
  }

  /**
   * Construct soap client using given configuration
   *
   * @param soapConfig
   */
  public MagentoSoapClient(SoapConfig soapConfig) {
    this(soapConfig, new SoapCallFactory());
  }

  @PreDestroy
  public void destroy() {
    // close the connection to Magento API
    if (callFactory != null && sender != null) {
      // first, we need to logout the previous session
      if (sessionId != null) {
        OMElement logoutMethod = callFactory.createLogoutCall(sessionId);
        try {
          sender.sendReceive(logoutMethod);
        } catch (Exception e) {
          log.warn("Cannot logout Magento SOAP API from session " + sessionId, e);
        }
        sessionId = null;
      }
      try {
        sender.cleanupTransport();
        sender.cleanup();
      } catch (Exception e) {
        log.warn("Cannot cleanup Axis2 ServiceClient", e);
      }
      sender = null;
    }
  }

  @Override
  protected void finalize() throws Throwable {
    destroy();
    super.finalize();
  }

  @Override
  public SoapConfig getConfig() {
    return config;
  }

  /**
   * Use to change the connection parameters to API (apiUser, apiKey,
   * remoteHost)
   *
   * @param config the config to set
   * @deprecated, please create a new magento soap client instead.
   */
  @Deprecated
  public void setConfig(SoapConfig config) throws AxisFault {
    this.config = config;
    login();
  }

  @Override
  public <R> R callArgs(final ResourcePath path, Object[] args) throws AxisFault {
    final String pathString = path.getPath();
    return call(pathString, args);
  }

  @Override
  public <T, R> R callSingle(final ResourcePath path, T arg) throws AxisFault {
    if (arg != null && arg.getClass().isArray())
      log.warn("Passing array argument to callSingle {}, probably you want to call callArgs?", path);
    final String pathString = path.getPath();
    return call(pathString, new Object[]{arg});
  }

  @Override
  public <T, R> R callReallySingle(final ResourcePath path, T arg) throws AxisFault {
    if (arg != null && arg.getClass().isArray())
      log.warn("Passing array argument to callSingle {}, probably you want to call callArgs?", path);
    final String pathString = path.getPath();
    return call(pathString, arg);
  }

  /**
   * Dynamic version of call.
   *
   * @param pathString
   * @param args
   * @return
   * @throws AxisFault
   */
  @SuppressWarnings("unchecked")
  public <R> R call(final String pathString, Object args) throws AxisFault {
    // Convert array input to List<Object>
    if (args != null && args.getClass().isArray()) {
      args = Arrays.asList((Object[]) args);
    }
    log.info("Calling {} {} at {}@{} with session {}", new Object[]{pathString, args, config.getApiUser(), config.getRemoteHost(), sessionId});
    OMElement method = callFactory.createCall(sessionId, pathString, args);

    // try {
    // final StringWriter stringWriter = new StringWriter();
    // IndentingXMLStreamWriter xmlWriter = new
    // IndentingXMLStreamWriter(StaxUtilsXMLOutputFactory.newInstance().createXMLStreamWriter(stringWriter));
    // method.serialize(xmlWriter);
    // log.debug("Calling {}: {}", pathString, stringWriter);
    // } catch (Exception e) {
    // log.warn("Cannot serialize SOAP call XML {}", method);
    // }

    OMElement result = null;
    try {
      sender.getOptions().setTimeOutInMilliSeconds(20000);
      result = sender.sendReceive(method);
    } catch (AxisFault axisFault) {
      if (axisFault.getMessage().toUpperCase().indexOf("SESSION EXPIRED") >= 0) {
        log.info("call session expired: ", axisFault);
        login();
        result = sender.sendReceive(method);
      } else {
        if(axisFault.getMessage().toUpperCase().indexOf("READ TIMED OUT") >= 0) {
          log.info("Timeout: ", axisFault);
        } else {
          throw axisFault;
        }
      }
    }
    log.info("Called {} {} at {}@{} with session {}", new Object[]{pathString, args, config.getApiUser(), config.getRemoteHost(), sessionId});
    return (R) returnParser.parse(result.getFirstChildWithName(CALL_RETURN));
  }

  @Override
  public <R> R callNoArgs(final ResourcePath path) throws AxisFault {
    return callSingle(path, null);
  }

  @Override
  public Object multiCall(List<ResourcePath> path, List<Object> args) throws AxisFault {
    throw new UnsupportedOperationException("not implemented yet");
  }

  /**
   * Connect to the service using the current config
   */
  protected void login() throws AxisFault {

    if (isLoggedIn()) {
      // relogin
      logout();
    }

    OMElement loginMethod = callFactory.createLoginCall(this.config.getApiUser(), this.config.getApiKey());
    log.info("====================================== Logging in user: {}", this.config.getApiUser());
    OMElement loginResult = sender.sendReceive(loginMethod);

    sessionId = loginResult.getFirstChildWithName(LOGIN_RETURN).getText();
    log.info("====================================== Logged in sessionId: {}", sessionId);
  }

  /**
   * Creates and configures the HTTP connection used for Magento calls.
   *
   * @throws AxisFault on all errors.
   */
  protected void prepareConnection(final HttpConnectionManagerParams params) throws AxisFault {
    final Options connectOptions = new Options();
    connectOptions.setTo(new EndpointReference(config.getRemoteHost()));
    connectOptions.setTransportInProtocol(Constants.TRANSPORT_HTTP);
    connectOptions.setTimeOutInMilliSeconds(60000);
    connectOptions.setProperty(HTTPConstants.MC_GZIP_REQUEST, true);
    connectOptions.setProperty(HTTPConstants.MC_ACCEPT_GZIP, true);

    // add auth information, if enabled,
    if (config.isHttpAuthEnabled()) {
      final HttpTransportProperties.Authenticator auth = new HttpTransportProperties.Authenticator();
      auth.setUsername(config.getHttpUsername());
      auth.setPassword(config.getHttpPassword());
      connectOptions.setProperty(HTTPConstants.AUTHENTICATE, auth);
    }

    // to use the same tcp connection for multiple calls
    // workaround:
    // http://amilachinthaka.blogspot.com/2009/05/improving-axis2-client-http-transport.html
    final MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
    httpConnectionManager.setParams(params);

    final HttpClient httpClient = new HttpClient(httpConnectionManager);
    // prepare for Axis2 1.7+ / HttpClient 4.2+
    // ClientConnectionManager httpConnectionManager = new
    // PoolingClientConnectionManager();
    // HttpClient httpClient = new DefaultHttpClient(httpConnectionManager);
    connectOptions.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);
    connectOptions.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, httpClient);
    connectOptions.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);

    // activate HTTP proxy if enabled
    if (config.isHttpProxyEnabled()) {
      final HttpTransportProperties.ProxyProperties proxyProps = new HttpTransportProperties.ProxyProperties();
      proxyProps.setProxyName(config.getHttpProxyHost());
      proxyProps.setProxyPort(config.getHttpProxyPort());
      if (config.isHttpProxyAuthEnabled()) {
        proxyProps.setUserName(config.getHttpProxyUsername());
        proxyProps.setPassWord(config.getHttpProxyPassword());
      }
      connectOptions.setProperty(HTTPConstants.PROXY, proxyProps);
    }

    sender = new ServiceClient();
    sender.setOptions(connectOptions);

    // disable SOAP XML indenting for now, until I find out how to make it work
    // on OSGi ~Hendy
    // java.lang.ClassNotFoundException:
    // javanet.staxutils.StaxUtilsXMLOutputFactory not found by
    // org.apache.servicemix.bundles.stax-utils [115]
    // StAXUtils.setFactoryPerClassLoader(false);
    // sender.getServiceContext().setProperty(Constants.Configuration.MESSAGE_FORMATTER,
    // new SOAPMessageFormatter() {
    // @Override
    // public String formatSOAPAction(MessageContext msgCtxt,
    // OMOutputFormat format, String soapActionString) {
    // format.setStAXWriterConfiguration(new StAXWriterConfiguration() {
    // @Override
    // public XMLOutputFactory configure(XMLOutputFactory factory,
    // StAXDialect dialect) {
    // StaxUtilsXMLOutputFactory indentingFactory = new
    // StaxUtilsXMLOutputFactory(factory);
    // indentingFactory.setProperty(StaxUtilsXMLOutputFactory.INDENTING, true);
    // return indentingFactory;
    // }
    //
    // @Override
    // public String toString() {
    // return "StaxUtils";
    // }
    // });
    // return super.formatSOAPAction(msgCtxt, format, soapActionString);
    // }
    // });
  }

  /**
   * Logout from service, throws logout exception if failed
   *
   * @throws AxisFault
   */
  protected void logout() throws AxisFault {

    log.info("====================================== Log out sessionId:  {}", sessionId);
    // first, we need to logout the previous session
    try {
      final OMElement logoutMethod = callFactory.createLogoutCall(sessionId);
      final OMElement logoutResult = sender.sendReceive(logoutMethod);
      final Boolean logoutSuccess = Boolean.parseBoolean(logoutResult.getFirstChildWithName(LOGOUT_RETURN).getText());
      if (!logoutSuccess) {
        throw new RuntimeException("Error logging out");
      }
    } catch (AxisFault axisFault) {
      if (axisFault.getMessage().toUpperCase().indexOf("SESSION EXPIRED") < 0) {
        throw axisFault;
      }
      log.info("logout session expired: ", axisFault);
    }
    log.info("====================================== Logging out user: {}", this.config.getApiUser());
    sessionId = null;
  }

  /**
   * Are we currently logged in?
   */
  protected Boolean isLoggedIn() {
    return sessionId != null;
  }
}
