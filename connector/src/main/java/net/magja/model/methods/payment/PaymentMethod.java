package net.magja.model.methods.payment;

import net.magja.model.BaseMagentoModel;
import net.magja.soap.Configuration;

public class PaymentMethod extends BaseMagentoModel {

  private String value;

  private String label;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public Object serializeToApi(Configuration configuration) {
    return null;
  }
}
