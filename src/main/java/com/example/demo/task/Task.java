package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Task {

  /**
   * 每分钟执行一次
   *
   * @throws InterruptedException
   */
  @Scheduled(cron = "0 * * * * *")
  private void task01() throws InterruptedException {
    log.info("task01 start");
    Thread.sleep(2000);
    log.info("task01 end");
  }

  /**
   * 方法每次执行完毕后的15秒执行一次
   *
   * @throws InterruptedException
   */
  @Scheduled(fixedDelay = 1000 * 15)
  private void task02() throws InterruptedException {
    log.info("task02 start");
    Thread.sleep(2000);
    log.info("task02 end");
  }

}
