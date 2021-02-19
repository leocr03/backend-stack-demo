# Backend Stack Demo

This project provides samples of using of the main modern Java technology stack required for backend engineers. 

The idea is to keep it simple as much as possible.

So far, we have these items:

* Kafka (Kafka + ZooKeeper + KafkaDrop)
* RabbitMQ
* Spring WebFlux
* Gitlab Pipeline


## Services
In order to run the tests easier, GET endpoints were created to produce a value.

### Kafka
[GET] ````/kafka/{someNumber}````: produce a number value to Kafka (e.g: /kafka/5)

### RabbitMQ
[GET] ````/rabbitmq/{someNumber}````: produce a number value to RabbitMQ (e.g: /rabbitmq/4)

### Spring WebFlux
[GET] ````/webflux````: show available services to see Spring WebFlux working


## How to Start
```
    cd src/main/resources/kafka
    docker-compose -f docker-compose.yaml up --build
```