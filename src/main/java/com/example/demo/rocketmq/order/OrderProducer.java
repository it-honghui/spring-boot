package com.example.demo.rocketmq.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author honghui 2021/07/22
 */
public class OrderProducer {

  public static void main(String[] args) throws Exception {
    // 1、创建消息生产者producer，并制定生产者组名
    DefaultMQProducer producer = new DefaultMQProducer("group1");
    // 2、指定Nameserver地址
    producer.setNamesrvAddr("192.168.110.103:9876;192.168.110.103:9877");
    // 3、启动producer
    producer.start();
    // 4、构建消息集合
    List<OrderStep> orderSteps = OrderStep.buildOrders();
    // 5、发送消息
    for (int i = 0; i < orderSteps.size(); i++) {
      Message message = new Message("TopicOrder", "Order", "i" + i, ("Hello World " + i).getBytes());
      // 参数一：消息对象、参数二：消息队列的选择器、参数三：选择队列的业务标识（订单ID）
      SendResult sendResult = producer.send(message, new MessageQueueSelector() {
        /**
         * @param mqs：队列集合
         * @param msg：消息对象
         * @param arg：业务标识的参数
         * @return
         */
        @Override
        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
          long orderId = (long) arg;
          long index = orderId % mqs.size();
          return mqs.get((int) index);
        }
      }, orderSteps.get(i).getOrderId());

      System.out.println("send result:" + sendResult);
    }
    producer.shutdown();
  }

}
