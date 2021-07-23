package com.example.demo.rocketmq.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author honghui 2021/07/22
 */
public class OrderConsumer {

  public static void main(String[] args) throws MQClientException {
    // 1、创建消费者Consumer，制定消费者组名
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    // 2、指定Nameserver地址
    consumer.setNamesrvAddr("192.168.110.103:9876;192.168.110.103:9877");
    // 3、订阅主题Topic和Tag
    consumer.subscribe("TopicOrder", "*");
    // 5、注册消息监听器
    consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
      for (MessageExt msg : msgs) {
        System.out.println("Thread name:[" + Thread.currentThread().getName() + "]:" + new String(msg.getBody()));
      }
      return ConsumeOrderlyStatus.SUCCESS;
    });

    // 6、启动消费者
    consumer.start();

    System.out.println("consumer start success");
  }

}
