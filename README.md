# Backend Stack Demo

This project provides samples of using of the main Java technology stack required for backend engineers nowadays.

So far, we have these items:

* *Kafka (Kafka + ZooKeeper + KafkaDrop)*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *KafkaDrop* in order to see the message queue
    * Note: message persistence in Redis is shared with RabbitMQ
* *RabbitMQ (RabbitMQ + RabbitMQ Management)*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *RabbitMQ Management* in order to see the message queue
  * Note: message persistence in Redis is shared with Kafka
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

[GET] ````http://localhost:8080/kafka/produce/{someNumber}````: produce a number value to Kafka (e.g: /kafka/produce/5)

[GET] ````http://localhost:8080/kafka/list````: list messages that were produced and consumed by Kafka

[GET] ````http://localhost:19000````: access KafkaDrop

![kafka-1](https://i.imgur.com/HZ96xjz.png)

![kafka-2](https://i.imgur.com/9z5fAeX.png)

![kafka-3](https://i.imgur.com/H7FDYsB.png)


### Test RabbitMQ

[GET] ````http://localhost:8080/rabbit/produce/{someNumber}````: produce a number value to RabbitMQ (e.g: /rabbit/produce/5)

[GET] ````http://localhost:8080/rabbit/list````: list messages that were produced and consumed by RabbitMQ

[GET] ````http://localhost:15672````: access RabbitMQ Management

![rabbit-1](https://i.imgur.com/DUiDSlo.png)

![rabbit-2](https://i.imgur.com/UVqPOWX.png)


### To test Spring WebFlux

[GET] ````/reactive/kafka/list````: endpoint that reuses Kafka list service using ```Mono``` 

[GET] ````/reactive/rabbit/list````: endpoint that reuses Kafka list service using ```Flux``

