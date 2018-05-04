package net.magja.service.methods.payment;

import net.magja.model.methods.shipping.PaymentMethod;
import net.magja.service.GeneralService;
import net.magja.service.ServiceException;

import java.util.List;

public interface PaymentMethodRemoteService extends GeneralService<PaymentMethod> {

  /**
   * get all payment methods from magento
   *
   * @throws ServiceException
   */
  public abstract List<PaymentMethod> list() throws ServiceException;

}
