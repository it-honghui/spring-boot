package com.example.demo.rocketmq.batch;

import com.google.common.collect.Lists;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author honghui 2021/07/22
 */
public class BatchProducer {

  public static void main(String[] args) throws Exception {
    // 1、创建消息生产者producer，并制定生产者组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.110.103:9876;192.168.110.103:9877");
    // 3、启动producer
    producer.start();
    // 4、创建消息对象，指定主题Topic、Tag和消息体
    List<Message> msgList = Lists.newArrayList(
        new Message("topic-batch", "Tag1", ("Hello World " + 1).getBytes()),
        new Message("topic-batch", "Tag1", ("Hello World " + 2).getBytes()),
        new Message("topic-batch", "Tag1", ("Hello World " + 3).getBytes())
    );
    // 5、发送消息
    SendResult result = producer.send(msgList);
    // 6、发送结果
    System.out.println("send result:" + result);
    // 7、线程睡5秒
    TimeUnit.SECONDS.sleep(5);
    // 8、关闭生产者producer
    producer.shutdown();
  }

}
