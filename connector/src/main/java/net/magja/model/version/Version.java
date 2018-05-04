package net.magja.model.version;

import net.magja.model.BaseMagentoModel;
import net.magja.soap.Configuration;

public class Version extends BaseMagentoModel {

  private static final long serialVersionUID = -3154289809263844811L;

  private String versionNumber;

  public String getVersionNumber() {
    return versionNumber;
  }

  public void setVersionNumber(String versionNumber) {
    this.versionNumber = versionNumber;
  }

  @Override
  public Object serializeToApi(Configuration configuration) {
    return null;
  }
}
