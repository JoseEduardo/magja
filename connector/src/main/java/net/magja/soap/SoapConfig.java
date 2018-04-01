package net.magja.soap;

import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.Serializable;

/**
 * Configuration of the SOAP Client.
 *
 * @author Simon Zambrovski
 */
public class SoapConfig implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long idConfiguration;
  private String apiUser;
  private String apiKey;
  private String remoteHost;
  private Integer defaultAttributeSetId;
  private Integer defaultRootCategoryId;
  private boolean httpProxyEnabled;
  private String httpProxyHost;
  private Short httpProxyPort;
  private boolean httpProxyAuthEnabled;
  private String httpProxyPassword;
  private String httpProxyUsername;
  private boolean httpAuthEnabled;
  private String httpUsername;
  private String httpPassword;

  private HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();

  public SoapConfig() {

  }

  public SoapConfig(String apiUser, String apiKey, String remoteHost) {
    this.apiUser = apiUser;
    this.apiKey = apiKey;
    this.remoteHost = remoteHost;
    this.defaultAttributeSetId = 4;
    this.defaultRootCategoryId = 2;
  }

  public SoapConfig(final Configuration properties) {
    this.idConfiguration = properties.getId();
    this.apiUser = properties.getApiUser();
    this.apiKey = properties.getApiKey();
    this.remoteHost = properties.getRemoteHost();

    this.httpAuthEnabled = properties.isHttpAuthEnabled();
    this.httpUsername = properties.getHttpUsername();
    this.httpPassword = properties.getHttpPassword();

    this.defaultAttributeSetId = properties.getDefaultAttributeSetId();
    this.defaultRootCategoryId = properties.getDefaultRootCategoryId();

    this.httpProxyEnabled = properties.isHttpProxyEnabled();
    this.httpProxyHost = properties.getHttpProxyHost();
    this.httpProxyPort = properties.getHttpProxyPort();

    this.httpProxyAuthEnabled = properties.isHttpProxyAuthEnabled();
    this.httpProxyUsername = properties.getHttpProxyUsername();
    this.httpProxyPassword = properties.getHttpProxyPassword();

  }

  public Long getIdConfiguration() {
    return idConfiguration;
  }

  public void setIdConfiguration(Long idConfiguration) {
    this.idConfiguration = idConfiguration;
  }

  public HttpConnectionManagerParams getHttpConnectionManagerParams() {
    return httpConnectionManagerParams;
  }

  public void setHttpConnectionManagerParams(HttpConnectionManagerParams httpConnectionManagerParams) {
    this.httpConnectionManagerParams = httpConnectionManagerParams;
  }

  public void setDefaultMaxConnectionsPerHost(int maxConnectionsPerHost) {
    getHttpConnectionManagerParams().setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
  }

  public String getApiUser() {
    return apiUser;
  }

  public void setApiUser(String apiUser) {
    this.apiUser = apiUser;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getRemoteHost() {
    return remoteHost;
  }

  public void setRemoteHost(String remoteHost) {
    this.remoteHost = remoteHost;
  }

  public Integer getDefaultAttributeSetId() {
    return defaultAttributeSetId;
  }

  public void setDefaultAttributeSetId(Integer defaultAttributeSetId) {
    this.defaultAttributeSetId = defaultAttributeSetId;
  }

  public Integer getDefaultRootCategoryId() {
    return defaultRootCategoryId;
  }

  public void setDefaultRootCategoryId(Integer defaultRootCategoryId) {
    this.defaultRootCategoryId = defaultRootCategoryId;
  }

  public boolean isHttpProxyEnabled() {
    return this.httpProxyEnabled;
  }

  public String getHttpProxyHost() {
    return this.httpProxyHost;
  }

  public Short getHttpProxyPort() {
    return this.httpProxyPort;
  }

  public boolean isHttpProxyAuthEnabled() {
    return this.httpProxyAuthEnabled;
  }

  public String getHttpProxyUsername() {
    return this.httpProxyUsername;
  }

  public String getHttpProxyPassword() {
    return this.httpProxyPassword;
  }

  public void setHttpProxyEnabled(boolean httpProxyEnabled) {
    this.httpProxyEnabled = httpProxyEnabled;
  }

  public void setHttpProxyHost(String httpProxyHost) {
    this.httpProxyHost = httpProxyHost;
  }

  public void setHttpProxyPassword(String httpProxyPassword) {
    this.httpProxyPassword = httpProxyPassword;
  }

  public void setHttpProxyUsername(String httpProxyUsername) {
    this.httpProxyUsername = httpProxyUsername;
  }

  public void setHttpProxyAuthEnabled(boolean httpProxyAuthEnabled) {
    this.httpProxyAuthEnabled = httpProxyAuthEnabled;
  }

  public void setHttpProxyPort(Short httpProxyPort) {
    this.httpProxyPort = httpProxyPort;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idConfiguration == null) ? 0 : idConfiguration.hashCode());
    result = prime * result + ((apiUser == null) ? 0 : apiUser.hashCode());
    result = prime * result + ((defaultAttributeSetId == null) ? 0 : defaultAttributeSetId.hashCode());
    result = prime * result + ((defaultRootCategoryId == null) ? 0 : defaultRootCategoryId.hashCode());
    result = prime * result + (httpAuthEnabled ? 1231 : 1237);
    result = prime * result + (httpProxyAuthEnabled ? 1231 : 1237);
    result = prime * result + (httpProxyEnabled ? 1231 : 1237);
    result = prime * result + ((httpProxyHost == null) ? 0 : httpProxyHost.hashCode());
    result = prime * result + ((httpProxyPort == null) ? 0 : httpProxyPort.hashCode());
    result = prime * result + ((httpProxyUsername == null) ? 0 : httpProxyUsername.hashCode());
    result = prime * result + ((httpUsername == null) ? 0 : httpUsername.hashCode());
    result = prime * result + ((remoteHost == null) ? 0 : remoteHost.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SoapConfig other = (SoapConfig) obj;
    if (idConfiguration == null) {
      if (other.idConfiguration != null) {
        return false;
      }
    } else if (!idConfiguration.equals(other.idConfiguration)) {
      return false;
    }

    if (apiUser == null) {
      if (other.apiUser != null) {
        return false;
      }
    } else if (!apiUser.equals(other.apiUser)) {
      return false;
    }
    if (defaultAttributeSetId == null) {
      if (other.defaultAttributeSetId != null) {
        return false;
      }
    } else if (!defaultAttributeSetId.equals(other.defaultAttributeSetId)) {
      return false;
    }
    if (defaultRootCategoryId == null) {
      if (other.defaultRootCategoryId != null) {
        return false;
      }
    } else if (!defaultRootCategoryId.equals(other.defaultRootCategoryId)) {
      return false;
    }
    if (httpAuthEnabled != other.httpAuthEnabled) {
      return false;
    }
    if (httpProxyAuthEnabled != other.httpProxyAuthEnabled) {
      return false;
    }
    if (httpProxyEnabled != other.httpProxyEnabled) {
      return false;
    }
    if (httpProxyHost == null) {
      if (other.httpProxyHost != null) {
        return false;
      }
    } else if (!httpProxyHost.equals(other.httpProxyHost)) {
      return false;
    }
    if (httpProxyPort == null) {
      if (other.httpProxyPort != null) {
        return false;
      }
    } else if (!httpProxyPort.equals(other.httpProxyPort)) {
      return false;
    }
    if (httpProxyUsername == null) {
      if (other.httpProxyUsername != null) {
        return false;
      }
    } else if (!httpProxyUsername.equals(other.httpProxyUsername)) {
      return false;
    }
    if (httpUsername == null) {
      if (other.httpUsername != null) {
        return false;
      }
    } else if (!httpUsername.equals(other.httpUsername)) {
      return false;
    }
    if (remoteHost == null) {
      if (other.remoteHost != null) {
        return false;
      }
    } else if (!remoteHost.equals(other.remoteHost)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "SoapConfig [remoteHost=" + remoteHost + ", idConfiguration=" + idConfiguration + ", apiUser=" + apiUser + ", defaultAttributeSetId=" + defaultAttributeSetId + ", defaultRootCategoryId="
      + defaultRootCategoryId + ", httpProxyEnabled=" + httpProxyEnabled + ", httpProxyHost=" + httpProxyHost + ", httpProxyPort=" + httpProxyPort
      + ", httpProxyAuthEnabled=" + httpProxyAuthEnabled + ", httpProxyUsername=" + httpProxyUsername + ", httpAuthEnabled=" + httpAuthEnabled
      + ", httpUsername=" + httpUsername + "]";
  }

  public boolean isHttpAuthEnabled() {
    return httpAuthEnabled;
  }

  public void setHttpAuthEnabled(boolean httpAuthEnabled) {
    this.httpAuthEnabled = httpAuthEnabled;
  }

  public String getHttpUsername() {
    return httpUsername;
  }

  public void setHttpUsername(String httpUsername) {
    this.httpUsername = httpUsername;
  }

  public String getHttpPassword() {
    return httpPassword;
  }

  public void setHttpPassword(String httpPassword) {
    this.httpPassword = httpPassword;
  }

}
