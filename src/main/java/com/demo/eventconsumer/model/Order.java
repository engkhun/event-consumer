package com.demo.eventconsumer.model;
import lombok.Data;

@Data
public class Order {
  private Integer id;
  private String name;
  private String data;
  private byte[] bytePayload;
}
