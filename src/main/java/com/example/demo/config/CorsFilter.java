package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author honghui 2023/02/03
 * 多个过滤器可以使用 @Order(1) 指定过滤器的执行顺序,值越大越靠后执行
 * @WebFilter(filterName = "CorsFilter", urlPatterns = "/book") 需要结合启动类的 @ServletComponentScan 注解或者只使用 @Component 注解
 *
 */
@Slf4j
@Component
public class CorsFilter implements Filter {

  /**
   * 一般用于：
   * Session校验
   * 判断用户权限
   * 不符合设定条件，则会被重定向特殊的地址或者设定的响应
   * 过滤敏感词汇
   * 设置编码
   */
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    // 支持跨域
    response.setHeader("Access-Control-Allow-Origin", "*");
    // 跨域请求允许设置cookie
    response.setHeader("Access-Control-Allow-Credentials", "true");
    // 设置跨域请求的类型
    response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
    // 表示隔60分钟才发起预检请求
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void init(FilterConfig filterConfig) {
    log.info("初始化过滤器!");
  }

  @Override
  public void destroy() {
    log.info("销毁过滤器!（结束SpringBoot程序时才销毁）");
  }

}
