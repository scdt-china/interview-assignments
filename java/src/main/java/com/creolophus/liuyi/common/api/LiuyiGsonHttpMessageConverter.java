package com.creolophus.liuyi.common.api;

import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

/**
 * @author magicnana
 * @date 2020/12/16 5:43 PM
 */
public class LiuyiGsonHttpMessageConverter extends GsonHttpMessageConverter {

  @Override
  public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {

    Api api = ApiContext.getContext().getApi();
    if(api!=null && StringUtils.isNotBlank(api.scope()) && Api.SCOPE_INTER.equalsIgnoreCase(api.scope())){
      return super.canRead(type, contextClass, mediaType);
    }else{
      return false;
    }
  }

  @Override
  public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
    Api api = ApiContext.getContext().getApi();
    if(api!=null && StringUtils.isNotBlank(api.scope()) && Api.SCOPE_INTER.equalsIgnoreCase(api.scope())){
      return super.canWrite(type, clazz, mediaType);
    }else{
      return false;
    }
  }
}
