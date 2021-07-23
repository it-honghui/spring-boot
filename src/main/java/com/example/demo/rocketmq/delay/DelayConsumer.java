package com.example.demo.rocketmq.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author honghui 2021/07/22
 */
public class DelayConsumer {

  public static void main(String[] args) throws Exception {
    // 1、创建消费者Consumer，制定消费者组名
    DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
    // 2、指定Nameserver地址
    consumer.setNamesrvAddr("192.168.110.103:9876;192.168.110.103:9877");
    // 3、订阅主题Topic和Tag
    consumer.subscribe("TopicDelay", "*");
    // 5、.设置回调函数，处理消息
    consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
      for (MessageExt msg : msgs) {
        System.out.println("Msg ID:[" + msg.getMsgId() + "],delay time：" + (System.currentTimeMillis() - msg.getStoreTimestamp()));
      }
      return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    });
    // 6、启动消费者
    consumer.start();

    System.out.println("consumer start success");
  }

}
