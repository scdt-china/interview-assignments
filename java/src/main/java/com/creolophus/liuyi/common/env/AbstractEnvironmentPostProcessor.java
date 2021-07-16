package com.creolophus.liuyi.common.env;

import java.util.Map;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * @author magicnana
 * @date 2019/9/26 下午2:56
 */
public abstract class AbstractEnvironmentPostProcessor implements EnvironmentPostProcessor {

  public void addOrReplace(
      MutablePropertySources propertySources,
      Map<String, Object> map,
      String propertySourceName) {
    MapPropertySource target = null;
    if (propertySources.contains(propertySourceName)) {
      PropertySource<?> source = propertySources.get(propertySourceName);
      if (source instanceof MapPropertySource) {
        target = (MapPropertySource) source;
        for (String key : map.keySet()) {
          if (!target.containsProperty(key)) {
            target.getSource().put(key, map.get(key));
          }
        }
      }
    }
    if (target == null) {
      target = new MapPropertySource(propertySourceName, map);
    }
    if (!propertySources.contains(propertySourceName)) {
      propertySources.addLast(target);
    }
  }
}
