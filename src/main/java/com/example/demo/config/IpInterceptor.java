package com.example.demo.config;

import cn.hutool.json.JSONUtil;
import com.example.demo.domain.R;
import com.example.demo.domain.Recode;
import com.example.demo.util.ServerUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class IpInterceptor implements HandlerInterceptor {

  @Value("${ipLimit}")
  private int ipLimit;

  private final LoadingCache<String, AtomicInteger> ipCache = CacheBuilder.newBuilder()
      .expireAfterWrite(1, TimeUnit.MINUTES)
      .build(new CacheLoader<String, AtomicInteger>() {
        @NonNull
        @Override
        public AtomicInteger load(@NonNull String ip) {
          return new AtomicInteger(0);
        }
      });

  @SneakyThrows
  @Override
  public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
    String ip = ServerUtil.getIp(request);
    AtomicInteger count = ipCache.get(ip);
    if (count.incrementAndGet() > ipLimit) {
      log.info("TOO_MANY_REQUESTS {} {}", request.getMethod(), ip);
      response.setCharacterEncoding("utf-8");
      response.setContentType("application/json");
      response.getWriter().write(JSONUtil.toJsonStr(R.error(Recode.TOO_MANY_REQUEST_ERROR)));
      return false;
    }
    return true;
  }

}
