# Event-Driven Microservices
## Prerequisites

### Download IntelliJ IDEA:

https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&amp;code=IIC

### Setup Apache kafka:

Download apache kafka:[https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)

1.)Start Zookeeper server

> - Go to C:\kafka\kafka\_2.12-2.7.0\bin\windows
> - Run this cmd: zookeeper-server-start.bat C:\Kafka\kafka\_2.12-2.7.0\config\zookeeper.properties

2.)Start Kafka server

> - Go to C:\kafka\kafka\_2.12-2.7.0\bin\windows
> - Run this cmd: kafka-server-start.bat C:\Kafka\kafka\_2.12-2.7.0\config\server.properties

## Bootstrap your application with Spring Initializr.

[https://start.spring.io/](https://start.spring.io/)

![image](https://user-images.githubusercontent.com/37209814/106872542-6e3a1480-66a1-11eb-95ae-6ec7a54b453d.png)

## Spring cloud stream with functional way

Functional Interface Input/Output can be defined through the following rules in the application.yaml .

```yaml
•	Input : (function name) + -in- + (index)
•	Output : (function name) + -out- + (index)
```
For example, orderingEventConsumer-in-0 in the example below defines a binding for function orderingEventConsumer that is an input that subscribes to a channel with the data it receives in the first input parameter (index 0).

### Application.yaml
```yaml
spring:
  cloud:
    function:
      definition: orderingEventConsumer;orderingEventProducer
    stream:
      kafka:
        binder:
          brokers: localhost:9092
      bindings:
        orderingEventConsumer-in-0:
          destination: data_stream
          group: eventConsumer-dev-local
        orderingEventProducer-out-0:
          destination: data_stream

```

### Producer:
Event producer: 
For producing message, using rest controller and putting the message on the topic(data_stream).

Writing a controller, the Controller is used to handle the incoming HTTP request from the user and returns an appropriate response.

A producer can be written with the supplier, but you need a bridge for this. Here we create the Message and put it into EmitterProcessor then publish it to the output binding.

### Consumer:

With **`@Bean`** registered as functional interface, you can simply process the message as below.
```java
@Bean
public Consumer<Order> orderingEventConsumer() {
  return order -> orderingService.processOrderEvent(order);
}

```

### Run the application from your IDE

Using Postman to create the ordering event Payload :

POST: [http://localhost:8080/api/event/createOrder](http://localhost:8080/api/event/createOrder)

### Sample payload:
```json
{
  "id": "1",
  "name": "demo",
  "data": "{'payload':{'product':'food','brand':'xxx','quantity':'2'}}"
}

```

Check the log to make sure the application receive the event message.

