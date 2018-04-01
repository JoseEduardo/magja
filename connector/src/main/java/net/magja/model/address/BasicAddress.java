/**
 * @author andre
 */
package net.magja.model.address;

import net.magja.soap.Configuration;

import java.util.Map;

@SuppressWarnings("serial")
public class BasicAddress extends Address<Map<String, Object>> {

  @Override
  public Map<String, Object> serializeToApi(Configuration configuration) {
    Map<String, Object> props = getAllProperties();
    return props;
  }

}
