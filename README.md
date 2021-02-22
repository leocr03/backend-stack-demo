# Backend Stack Demo

This project provides samples of using of the main modern Java technology stack required for backend engineers. 

The idea is to keep it simple as much as possible.

So far, we have these items:

* Kafka (Kafka + ZooKeeper + KafkaDrop)
* RabbitMQ
* Spring WebFlux
* Gitlab Pipeline


## How to Use
In order to run the tests easier, GET endpoints were created to produce a value.

### Kafka
[GET] ````/kafka/produce/{someNumber}````: produce a number value to Kafka (e.g: /kafka/5)

[GET] ````/kafka/list````: list messages

![kafka-1](https://i.imgur.com/HZ96xjz.png)

![kafka-2](https://i.imgur.com/9z5fAeX.png)

![kafka-3](https://i.imgur.com/H7FDYsB.png)


### RabbitMQ
[GET] ````/rabbitmq/produce/{someNumber}````: produce a number value to RabbitMQ (e.g: /rabbitmq/4)

![rabbit-1](https://i.imgur.com/DUiDSlo.png)

![rabbit-2](https://i.imgur.com/UVqPOWX.png)


### Spring WebFlux
[GET] ````/reactive/kafka/list````: show available services to see Spring WebFlux working
[GET] ````/reactive/rabbit/list````: show available services to see Spring WebFlux working


## How to Start
```
    cd src/main/resources/kafka
    docker-compose -f docker-compose.yaml up --build
```
