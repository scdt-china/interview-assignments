package com.creolophus.liuyi.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author magicnana
 * @date 2019/6/12 上午9:41
 */
public class IPUtil {

  private static String ip;

  public static String getLocalIP() {
    if (StringUtils.isBlank(ip)) {
      InetAddress localHost = null;
      try {
        localHost = Inet4Address.getLocalHost();
      } catch (UnknownHostException e) {
        return "----";
      }
      ip = localHost.getHostAddress();
      return ip;
    } else {
      return ip;
    }

  }

  public static String getRemoteIP(HttpServletRequest request) {
    try {
      //HttpServletRequest request = HttpContextHolder.getRequest();
      String ip;
      ip = request.getHeader("X-Real-IP");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("X-Forwarded-For");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_FORWARDED");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_CLIENT_IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_FORWARDED_FOR");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_FORWARDED");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("HTTP_VIA");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("REMOTE_ADDR");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
      }
      return ip;
    } catch (Exception e) {
    }
    return null;
  }
}
