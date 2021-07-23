package com.example.demo.rocketmq.order;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author honghui 2021/07/22
 */
@Data
public class OrderStep {

  private long orderId;
  private String desc;

  public static List<OrderStep> buildOrders() {
    List<OrderStep> orderList = new ArrayList<>();

    OrderStep orderDemo = new OrderStep();
    orderDemo.setOrderId(1111L);
    orderDemo.setDesc("创建");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(2222L);
    orderDemo.setDesc("创建");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(3333L);
    orderDemo.setDesc("创建");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(1111L);
    orderDemo.setDesc("付款");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(2222L);
    orderDemo.setDesc("付款");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(3333L);
    orderDemo.setDesc("付款");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(1111L);
    orderDemo.setDesc("推送");
    orderList.add(orderDemo);

    orderDemo = new OrderStep();
    orderDemo.setOrderId(1111L);
    orderDemo.setDesc("完成");
    orderList.add(orderDemo);

    return orderList;
  }
}
