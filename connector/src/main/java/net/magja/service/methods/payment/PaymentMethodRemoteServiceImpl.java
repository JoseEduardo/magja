package net.magja.service.methods.payment;

import net.magja.magento.ResourcePath;
import net.magja.model.methods.payment.PaymentMethod;
import net.magja.service.GeneralServiceImpl;
import net.magja.service.ServiceException;
import net.magja.soap.SoapClient;
import org.apache.axis2.AxisFault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentMethodRemoteServiceImpl extends GeneralServiceImpl<PaymentMethod> implements PaymentMethodRemoteService {

  public PaymentMethodRemoteServiceImpl(SoapClient soapClient) {
    super(soapClient);
  }

  @Override
  public List<PaymentMethod> list() throws ServiceException {
    List<PaymentMethod> resultList = new ArrayList<PaymentMethod>();
    List<Map<String, Object>> remote_result;
    try {
      remote_result = (List<Map<String, Object>>) soapClient.callSingle(ResourcePath.PaymentMethods, null);
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (remote_result == null)
      return resultList;

    for (Map<String, Object> att : remote_result) {
      PaymentMethod set = new PaymentMethod();
      for (Map.Entry<String, Object> attribute : att.entrySet())
        set.set(attribute.getKey(), attribute.getValue());
      resultList.add(set);
    }
    return resultList;
  }

}
