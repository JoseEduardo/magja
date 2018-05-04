package net.magja.service.version;

import net.magja.magento.ResourcePath;
import net.magja.model.version.Version;
import net.magja.service.GeneralServiceImpl;
import net.magja.service.ServiceException;
import net.magja.soap.SoapClient;
import org.apache.axis2.AxisFault;

import java.util.Map;


public class VersionRemoteServiceImpl extends GeneralServiceImpl<Version> implements VersionRemoteService {

  public VersionRemoteServiceImpl(SoapClient soapClient) {
    super(soapClient);
  }

  private Version buildVersion(Map<String, Object> attributes) {
    Version version = new Version();
    version.setVersionNumber(attributes.get("website_id").toString());

    return version;
  }

  @Override
  public Version get() throws ServiceException {
    Map<String, Object> remote_result = null;
    try {
      remote_result = (Map<String, Object>) soapClient.callSingle(ResourcePath.Version, null);
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (remote_result == null)
      return null;
    else
      return buildVersion(remote_result);
  }
}
