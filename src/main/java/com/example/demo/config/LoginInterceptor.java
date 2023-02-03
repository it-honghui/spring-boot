package com.example.demo.config;

import com.alibaba.fastjson.JSON;
import com.example.demo.domain.R;
import com.example.demo.domain.Recode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author honghui 2023/02/03
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
    log.info("请求路径： {}", request.getRequestURL());
    String token = request.getHeader("TOKEN");
    log.info("token：{}", token);
    if (!"1234".equals(token)) {
      response.setCharacterEncoding("utf-8");
      response.setContentType("application/json");
      response.getWriter().write(JSON.toJSONString(R.error(Recode.PASSWORD_ERROR)));
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) {
    log.info("Controller 方法调用之后执行");
  }

  @Override
  public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) {
    log.info("该方法将在整个请求结束之后执行");
  }

}
