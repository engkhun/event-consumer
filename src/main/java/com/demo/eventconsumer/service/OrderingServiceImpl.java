package com.demo.eventconsumer.service;

import com.demo.eventconsumer.model.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderingServiceImpl implements  OrderingService{

  @Override
  public  byte[] createOrderEvent(Order order) {
    return order.getData().getBytes();

  }

  @Override
  public void processOrderEvent(Order order) {
    System.out.println("Received: " + order);

  }
}
