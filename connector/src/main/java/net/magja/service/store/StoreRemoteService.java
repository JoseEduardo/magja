package net.magja.service.store;

import net.magja.model.store.Store;
import net.magja.service.GeneralService;
import net.magja.service.ServiceException;

import java.util.List;

public interface StoreRemoteService extends GeneralService<Store> {

  /**
   * create an order from cart
   *
   * @param store
   * @throws ServiceException
   */
  public abstract Store info(Store store) throws ServiceException;

  /**
   * create an order from cart
   *
   * @throws ServiceException
   */
  public abstract List<Store> list() throws ServiceException;

}
