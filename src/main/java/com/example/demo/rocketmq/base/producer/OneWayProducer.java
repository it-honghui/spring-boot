package com.example.demo.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送单向消息（2m-2s-sync）
 *
 * @author honghui 2021/07/22
 */
public class OneWayProducer {

  public static void main(String[] args) throws Exception {
    // 1、创建消息生产者producer，并制定生产者组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.137.129:9876;192.168.137.130:9876");
    // 3、启动producer
    producer.start();

    for (int i = 0; i < 3; i++) {
      // 4、创建消息对象，指定主题Topic、Tag和消息体
      Message msg = new Message("TopicBase", "Tag3", ("Hello World OneWayMessage " + i).getBytes());
      // 5、发送单向消息
      producer.sendOneway(msg);
      // 6、线程睡5秒
      TimeUnit.SECONDS.sleep(5);
    }
    // 7、关闭生产者producer
    producer.shutdown();
  }

}
