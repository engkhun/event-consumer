package com.demo.eventconsumer.config;

import com.demo.eventconsumer.model.Order;
import com.demo.eventconsumer.service.OrderingService;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderingEventConfig {
  @Autowired
  private OrderingService orderingService;

  @Bean
  public Consumer<Order> orderingEventConsumer() {
    //public Consumer<Message<byte[]>> eventConsumer() {
    return order -> orderingService.processOrderEvent(order);
  }
}
