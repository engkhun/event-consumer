package com.demo.eventconsumer.controller;

import com.demo.eventconsumer.model.Order;
import com.demo.eventconsumer.service.OrderingService;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

/*
The Controller is used to handle the incoming HTTP request from the user
 */
@RestController
@RequestMapping("/api/event")
public class OrderingController {

  @Autowired
  private OrderingService orderingService;

  /*
  @PostMapping("/produce")
  public boolean sendEventWithHeader(@RequestBody EventMessage msg) {
    msg.setBytePayload(msg.getData().getBytes());
    return StreamBridge.send("eventProducer-out-0",
        MessageBuilder.withPayload(msg)
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
  }

   */

  private final EmitterProcessor<Message<Order>> processor = EmitterProcessor.create();

  @PostMapping("/createOrder")
  public boolean createOrder(@RequestBody Order order) {
    try{
      order.setBytePayload(orderingService.createOrderEvent(order));
      Message<Order> message = MessageBuilder.withPayload(order)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build();

      processor.onNext(message);
      return true;
    }catch(Exception ex){
      System.out.println("\nerror="+ex.getMessage());
      return false;
    }
  }

  @Bean
  public Supplier<Flux<?>> orderingEventProducer() {
    return () -> processor;
  }

}
