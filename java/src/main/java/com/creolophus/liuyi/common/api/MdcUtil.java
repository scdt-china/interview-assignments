package com.creolophus.liuyi.common.api;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

/**
 * @author magicnana
 * @date 2020/1/16 下午2:44
 */
public class MdcUtil {

  public static final String MDC_METHOD = "X-LiuYi-Method";
  public static final String MDC_EXT = "X-LiuYi-EXT";
  public static final String MDC_URI = "X-LiuYi-URI";
  public static final String MDC_IP = "X-LiuYi-IP";


  public static final String MDC_DEFAULT = "-";

  public static void clearExt() {
    MDC.remove(MDC_EXT);
  }

  public static void clearAll() {
    MDC.clear();
  }

  public static void setExt(String ext) {
    if (StringUtils.isNotBlank(ext)) {
      MDC.put(MDC_EXT, ext);
    }
  }

  public static void setMethod() {
    if (StringUtils.isBlank(MDC.get(MDC_METHOD))) {
      MDC.put(MDC_METHOD, MDC_DEFAULT);
    }
  }

  public static void setMethod(String method) {
    if (StringUtils.isNotBlank(method)) {
      MDC.put(MDC_METHOD, method);
    }
  }

  public static void clearMethod() {
    MDC.remove(MDC_METHOD);
  }

  public static void setUri() {
    if (StringUtils.isBlank(MDC.get(MDC_URI))) {
      MDC.put(MDC_URI, MDC_DEFAULT);
    }
  }

  public static void setUri(String uri) {
    if (StringUtils.isNotBlank(uri)) {
      MDC.put(MDC_URI, uri);
    }
  }

  public static void clearUri() {
    MDC.remove(MDC_URI);
  }

}
