package com.example.demo.rocketmq.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息（2m-2s-sync）
 *
 * @author honghui 2021/07/22
 */
public class AsyncProducer {

  public static void main(String[] args) throws Exception {
    // 1、创建消息生产者producer，并制定生产者组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.110.103:9876;192.168.110.103:9877");
    // 3、启动producer
    producer.start();

    for (int i = 0; i < 10; i++) {
      // 4、创建消息对象，指定主题Topic、Tag和消息体
      Message msg = new Message("TopicBase", "Tag2", ("Hello World " + i).getBytes());
      // 5、发送异步消息
      producer.send(msg, new SendCallback() {
        /**
         * 发送成功回调函数
         */
        public void onSuccess(SendResult sendResult) {
          System.out.println("send result:" + sendResult);
        }

        /**
         * 发送失败回调函数
         */
        public void onException(Throwable e) {
          System.out.println("send error:" + e);
        }
      });
      // 6、线程睡5秒
      TimeUnit.SECONDS.sleep(5);
    }
    // 7、关闭生产者producer
    producer.shutdown();
  }

}
