package com.example.demo.component;

import cn.hutool.json.JSONUtil;
import com.example.demo.constant.GuavaCacheKey;
import com.example.demo.domain.entity.Book;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author honghui 2021/07/09
 */
@Component
public class GuavaCacheComponent extends CacheLoader<GuavaCacheKey, String> {

  private final LoadingCache<GuavaCacheKey, String> cache = CacheBuilder.newBuilder()
      .expireAfterWrite(5, TimeUnit.MINUTES)
      .build(this);

  private final UserService userService;

  public GuavaCacheComponent(UserService userService) {
    this.userService = userService;
  }

  public void invalidateAll() {
    cache.invalidateAll();
  }

  public List<User> findUserList() {
    return JSONUtil.toList(JSONUtil.parseArray(getCache(GuavaCacheKey.USER)), User.class);
  }

  public List<User> findBookList() {
    return JSONUtil.toList(JSONUtil.parseArray(getCache(GuavaCacheKey.BOOK)), User.class);
  }

  private String getCache(GuavaCacheKey guavaCacheKey) {
    try {
      return cache.get(guavaCacheKey);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String load(@NonNull GuavaCacheKey guavaCacheKey) {
    Object value = _load(guavaCacheKey);
    if (value == null) {
      return null;
    }
    if (value instanceof String) {
      return (String) value;
    }
    return JSONUtil.toJsonStr(value);
  }

  private Object _load(GuavaCacheKey guavaCacheKey) {
    switch (guavaCacheKey) {
      case USER:
        return userService.findUserList();
      case BOOK:
        return Lists.newArrayList(
            Book.builder().id(1L).name("水浒传").price(10).build(),
            Book.builder().id(1L).name("西游记").price(12).build()
        );
    }
    return null;
  }

}
