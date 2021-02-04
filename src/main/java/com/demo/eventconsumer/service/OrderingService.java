package com.demo.eventconsumer.service;

import com.demo.eventconsumer.model.Order;

public interface OrderingService {

  public byte[] createOrderEvent(Order order);

  public void processOrderEvent(Order order);

}
