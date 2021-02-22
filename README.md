# Backend Stack Demo

This project provides samples of using of the main Java technology stack required for backend engineers nowadays.

So far, we have these items:

* *Kafka (Kafka + ZooKeeper + KafkaDrop)*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *KafkaDrop* in order to see the message queue
* *RabbitMQ (RabbitMQ + RabbitMQ Management)*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *RabbitMQ Management* in order to see the message queue
* *Spring WebFlux*
    * Just reusing some code and adapting to work with ```Mono``` and ```Flux```
    * Common reactive programming functions (map, filter, etc.) is out of scope.

## How to Use
In order to run the tests easily, GET endpoints were created to produce a value.

### Start Stack

To run the stack:

* Enter the folder: ```src/main/resources/docker```

* Run docker-compose to start: ```docker-compose -f docker-compose.yaml up --build```

### Test Kafka

[GET] ````/kafka/produce/{someNumber}````: produce a number value to Kafka (e.g: /kafka/5)

[GET] ````/kafka/list````: list messages

![kafka-1](https://i.imgur.com/HZ96xjz.png)

![kafka-2](https://i.imgur.com/9z5fAeX.png)

![kafka-3](https://i.imgur.com/H7FDYsB.png)


### Test RabbitMQ
[GET] ````/rabbitmq/produce/{someNumber}````: produce a number value to RabbitMQ (e.g: /rabbitmq/4)

![rabbit-1](https://i.imgur.com/DUiDSlo.png)

![rabbit-2](https://i.imgur.com/UVqPOWX.png)


### To test Spring WebFlux

[GET] ````/reactive/kafka/list````: show available services to see Spring WebFlux working

[GET] ````/reactive/rabbit/list````: show available services to see Spring WebFlux working


## How to Start
```
    cd src/main/resources/kafka
    docker-compose -f docker-compose.yaml up --build
```
