package com.example.demo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author honghui 2023/02/02
 * 在使用SpringBoot构建项目时，我们通常有一些预先数据的加载。那么SpringBoot提供了一个简单的方式来实现–CommandLineRunner
 */
@Slf4j
@Component
@Order(value = 1)
public class InitRunner implements CommandLineRunner {

  @Override
  public void run(String... args) {
    log.info(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 InitRunner order 1 <<<<<<<<<<<<<");
  }

}
