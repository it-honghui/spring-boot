package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author honghui 2023/02/03
 * 内含多个接口
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  private final LoginInterceptor loginAuthenticator;

  public WebMvcConfig(LoginInterceptor loginAuthenticator) {
    this.loginAuthenticator = loginAuthenticator;
  }

  /**
   * 注册拦截器
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginAuthenticator)
        .addPathPatterns("/book/**")
        .excludePathPatterns("/book/_search");
  }

}
