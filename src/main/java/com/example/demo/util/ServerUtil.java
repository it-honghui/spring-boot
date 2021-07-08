package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;

public class ServerUtil {
  public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Real-Ip");// 负载均衡下为小写
    if (invalidIp(ip))
      ip = request.getHeader("X-Forwarded-For");
    if (invalidIp(ip))
      ip = request.getRemoteAddr();
    return ip;
  }

  private static boolean invalidIp(String ip) {
    return ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
  }
}
