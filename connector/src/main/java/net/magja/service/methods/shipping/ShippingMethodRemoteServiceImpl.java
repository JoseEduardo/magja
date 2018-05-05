package net.magja.service.methods.shipping;

import net.magja.magento.ResourcePath;
import net.magja.model.methods.shipping.ShippingMethod;
import net.magja.service.GeneralServiceImpl;
import net.magja.service.ServiceException;
import net.magja.soap.SoapClient;
import org.apache.axis2.AxisFault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShippingMethodRemoteServiceImpl extends GeneralServiceImpl<ShippingMethod> implements ShippingMethodRemoteService {

  public ShippingMethodRemoteServiceImpl(SoapClient soapClient) {
    super(soapClient);
  }

  @Override
  public List<ShippingMethod> list() throws ServiceException {
    List<ShippingMethod> resultList = new ArrayList<ShippingMethod>();
    List<Map<String, Object>> remote_result;
    try {
      remote_result = (List<Map<String, Object>>) soapClient.callSingle(ResourcePath.ShippingMethods, null);
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (remote_result == null)
      return resultList;

    for (Map<String, Object> att : remote_result) {
      ShippingMethod set = new ShippingMethod();
      for (Map.Entry<String, Object> attribute : att.entrySet())
        set.set(attribute.getKey(), attribute.getValue());
      resultList.add(set);
    }
    return resultList;
  }
}
