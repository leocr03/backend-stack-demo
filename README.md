# Backend Stack Demo

This project provides examples of using the main Java technology stack needed by back-end engineers today.

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
* Redis
    * It's being used to save data consumed by Kafka
* MongoDB
    * It's being used to save data consumed by RabbitMQ


## How to Use

Note: In order to run the tests easily, *GET* endpoints were created to produce a value.

### Start Stack

To compile the project:

* ```gradle clean build```

    * ```-x test``` to build without test (faster)

To run the stack:

* Enter the folder: ```src/main/resources/docker```

* Run docker-compose to start: ```docker-compose -f docker-compose.yaml up --build```

### Test Kafka

To produce some values to Kafka, for instance:

* [http://localhost:8080/api/v1/kafka/message/1](http://localhost:8080/api/v1/kafka/message/1)

* [http://localhost:8080/api/v1/kafka/message/2](http://localhost:8080/api/v1/kafka/message/2)

* [http://localhost:8080/api/v1/kafka/message/3](http://localhost:8080/api/v1/kafka/message/3)

[http://localhost:8080/api/v1/kafka/messages](http://localhost:8080/api/v1/kafka/messages): list messages that were produced and consumed by Kafka

[http://localhost:19000](http://localhost:19000): access KafkaDrop

![kafka-1](https://i.imgur.com/HZ96xjz.png)

![kafka-2](https://i.imgur.com/9z5fAeX.png)

![kafka-3](https://i.imgur.com/H7FDYsB.png)

### Test RabbitMQ

To produce some values to RabbitMQ, for instance:

* [http://localhost:8080/api/v1/rabbit/message/10](http://localhost:8080/api/v1/rabbit/message/10)

* [http://localhost:8080/api/v1/rabbit/message/11](http://localhost:8080/api/v1/rabbit/message/11)

* [http://localhost:8080/api/v1/rabbit/message/12](http://localhost:8080/api/v1/rabbit/message/12)

[http://localhost:8080/api/v1/rabbit/messages](http://localhost:8080/api/v1/rabbit/messages): list messages that were produced and consumed by RabbitMQ

[http://localhost:15672](http://localhost:15672): access RabbitMQ Management (guest / guest)

![rabbit-1](https://i.imgur.com/DUiDSlo.png)

![rabbit-2](https://i.imgur.com/UVqPOWX.png)

### Test Spring WebFlux

* Produce some Kafka or RabbitMQ value 

* [http://localhost:8080/api/v1/reactive/kafka/messages](http://localhost:8080/api/v1/reactive/kafka/messages): endpoint that reuses Kafka list service using ```Mono``` 

* [http://localhost:8080/api/v1/reactive/rabbit/messages](http://localhost:8080/api/v1/reactive/rabbit/messages): endpoint that reuses Rabbit list service using ```Flux```

### Test Redis

* Run steps to produce values for Kafka

* Enter in CLI of Redis container: ```docker exec -it redis redis-cli```

* Please refer to [https://redis.io/commands](https://redis.io/commands). E.g.: ```KEYS *```

### Test Mongo

* Run steps to produce values for Mongo

* Enter in CLI of Mongo container: ```docker exec -it mongo mongo```

* Please refer to [https://docs.mongodb.com/manual/reference/mongo-shell/](https://docs.mongodb.com/manual/reference/mongo-shell/). E.g.: ```db.message.find()```
