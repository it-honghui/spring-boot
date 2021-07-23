package com.example.demo.rocketmq.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * @author honghui 2021/07/22
 */
public class TransactionProducer {

  public static void main(String[] args) throws Exception {
    // 1、创建消息生产者producer，并制定生产者组名
    TransactionMQProducer producer = new TransactionMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.137.129:9876;192.168.137.130:9876");
    // 3、添加事务监听器
    producer.setTransactionListener(new TransactionListener() {
      /**
       * 在该方法中执行本地事务
       * @param msg
       * @param arg
       * @return
       */
      @Override
      public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        if (StringUtils.equals("TAGA", msg.getTags())) {
          return LocalTransactionState.COMMIT_MESSAGE;
        } else if (StringUtils.equals("TAGB", msg.getTags())) {
          return LocalTransactionState.ROLLBACK_MESSAGE;
        } else if (StringUtils.equals("TAGC", msg.getTags())) {
          return LocalTransactionState.UNKNOW;
        }
        return LocalTransactionState.UNKNOW;
      }

      /**
       * 该方法时MQ进行消息事务状态回查
       * @param msg
       * @return
       */
      @Override
      public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        System.out.println("message Tag:" + msg.getTags());
        return LocalTransactionState.COMMIT_MESSAGE;
      }
    });

    // 4.启动producer
    producer.start();

    String[] tags = {"TAGA", "TAGB", "TAGC"};

    for (int i = 0; i < 3; i++) {
      // 4、创建消息对象，指定主题Topic、Tag和消息体
      Message msg = new Message("TopicTransaction", tags[i], ("Hello World " + i).getBytes());
      // 5、发送消息
      SendResult result = producer.sendMessageInTransaction(msg, null);

      System.out.println("send result:" + result);
      // 6、线程睡1秒
      TimeUnit.SECONDS.sleep(2);
    }

  }

}
