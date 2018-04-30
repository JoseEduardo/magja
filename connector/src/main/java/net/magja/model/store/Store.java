package net.magja.model.store;

import net.magja.model.BaseMagentoModel;
import net.magja.soap.Configuration;

public class Store extends BaseMagentoModel {

  private static final long serialVersionUID = -3154289809263844828L;

  private int storeId;
  private String code;
  private int websiteId;
  private int groupId;
  private String name;
  private int sortOrder;
  private int isActive;

  public int getStoreId() {
    return storeId;
  }

  public void setStoreId(int storeId) {
    this.storeId = storeId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getWebsiteId() {
    return websiteId;
  }

  public void setWebsiteId(int websiteId) {
    this.websiteId = websiteId;
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public int getIsActive() {
    return isActive;
  }

  public void setIsActive(int isActive) {
    this.isActive = isActive;
  }

  @Override
  public Object serializeToApi(Configuration configuration) {
    return null;
  }

  @Override
  public String toString() {
    return "Store{" +
      "storeId=" + storeId +
      ", code='" + code + '\'' +
      ", websiteId=" + websiteId +
      ", groupId=" + groupId +
      ", name='" + name + '\'' +
      ", sortOrder=" + sortOrder +
      ", isActive=" + isActive +
      '}';
  }
}
