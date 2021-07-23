package com.example.demo.rocketmq.delay;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @author honghui 2021/07/22
 */
public class DelayProducer {

  public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
    // 1、创建消息生产者producer，并制定生产者组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.137.129:9876;192.168.137.130:9876");
    // 3、启动producer
    producer.start();

    for (int i = 0; i < 10; i++) {
      // 4、创建消息对象，指定主题Topic、Tag和消息体
      Message msg = new Message("TopicDelay", "Tag1", ("Hello World " + i).getBytes());
      // 5、设定延迟时间
      msg.setDelayTimeLevel(2);
      // 6、发送消息
      SendResult result = producer.send(msg);
      // 7、发送结果
      System.out.println("send result:" + result);
      // 8、线程睡1秒
      TimeUnit.SECONDS.sleep(1);
    }
    // 9、关闭生产者producer
    producer.shutdown();
  }

}
