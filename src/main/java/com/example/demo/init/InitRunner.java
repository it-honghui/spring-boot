package com.example.demo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author honghui 2023/02/02
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
