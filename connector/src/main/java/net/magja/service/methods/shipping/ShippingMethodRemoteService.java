package net.magja.service.methods.shipping;

import net.magja.model.methods.payment.ShippingMethod;
import net.magja.service.GeneralService;
import net.magja.service.ServiceException;

import java.util.List;

public interface ShippingMethodRemoteService extends GeneralService<ShippingMethod> {

  /**
   * get all shipping methods from magento
   *
   * @throws ServiceException
   */
  public abstract List<ShippingMethod> list() throws ServiceException;

}
