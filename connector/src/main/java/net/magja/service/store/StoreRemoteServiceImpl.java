package net.magja.service.store;

import net.magja.magento.ResourcePath;
import net.magja.model.store.Store;
import net.magja.service.GeneralServiceImpl;
import net.magja.service.ServiceException;
import net.magja.soap.SoapClient;
import org.apache.axis2.AxisFault;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreRemoteServiceImpl extends GeneralServiceImpl<Store> implements StoreRemoteService {


  public StoreRemoteServiceImpl(SoapClient soapClient) {
    super(soapClient);
  }

  private Store buildStore(Map<String, Object> attributes) {
    Store store = new Store();
    store.setWebsiteId(Integer.valueOf(attributes.get("website_id").toString()));
    store.setStoreId(Integer.valueOf(attributes.get("store_id").toString()));
    return store;
  }

  @Override
  public Store info(Store store) throws ServiceException {
    Map<String, Object> remote_result = null;
    try {
      remote_result = (Map<String, Object>) soapClient.callSingle(ResourcePath.StoreInfo, store.getId() == null ? store.getCode() : store.getId());
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (remote_result == null)
      return null;
    else
      return buildStore(remote_result);
  }

  @Override
  public List<Store> list() throws ServiceException {
    List<Store> resultList = new ArrayList<Store>();
    List<Map<String, Object>> remote_result;
    try {
      remote_result = (List<Map<String, Object>>) soapClient.callSingle(ResourcePath.StoreList, null);
    } catch (AxisFault e) {
      if (debug)
        e.printStackTrace();
      throw new ServiceException(e.getMessage());
    }

    if (remote_result == null)
      return resultList;

    for (Map<String, Object> att : remote_result) {
      Store set = new Store();
      for (Map.Entry<String, Object> attribute : att.entrySet())
        set.set(attribute.getKey(), attribute.getValue());
      resultList.add(set);
    }
    return resultList;
  }
}
