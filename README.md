# Backend Stack Demo

This project provides examples of using the main Java technology stack needed by back-end engineers today.

So far, we have these items:

* *[Gradle](https://spring.io/guides/gs/gradle/)*
    * Application is using gradle to be built.
* *[Spring-Boot](https://spring.io/guides/gs/spring-boot/)*
    * Application was created with Spring Boot using [Spring Initializr](https://start.spring.io/)
* *[Docker](https://www.docker.com/get-started)*
    * [Dockerfile](https://github.com/leocr03/backend-stack-demo/blob/main/Dockerfile) contains an example of the dockerization of an application (in this case *backend-stack-demo* application)
* *[Docker-Compose](https://docs.docker.com/compose/)*
    * [docker-compose.yaml](https://github.com/leocr03/backend-stack-demo/blob/main/src/main/resources/docker/docker-compose.yaml) contains an example of docker compose with all the applications needed.
* *Kafka ([Kafka](https://kafka.apache.org/documentation/) + [ZooKeeper](https://zookeeper.apache.org/) + [KafkaDrop](https://github.com/obsidiandynamics/kafdrop))*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *KafkaDrop* in order to see the message queue
* *RabbitMQ ([RabbitMQ](https://www.rabbitmq.com/tutorials/tutorial-one-java.html) + [RabbitMQ Management](https://www.rabbitmq.com/management.html))*
    * It has been created:
        * an endpoint to produce some value
        * a simple persistence in *Redis* to save the consumed values
        * an endpoint to list those values that were consumed and persisted
    * *RabbitMQ Management* in order to see the message queue
* *[Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux)*
    * Just reusing some code and adapting to work with ```Mono``` and ```Flux```
    * Common reactive programming functions (map, filter, etc.) is out of scope.
* *[Redis](https://redis.io/topics/quickstart)*
    * It's being used to save data consumed by Kafka
* *[MongoDB](https://docs.mongodb.com/manual/tutorial/getting-started/)*
    * It's being used to save data consumed by RabbitMQ


## Requirements

* JDK: > 11
* Gradle: > 6.8.2
    * Tip: you can use [sdkman](http://sdkman.io/) to install Java and Gradle (it's easy to manage a different version)
* Docker: > 20.10.3
* Docker-Compose: > 1.26.0


## How to Use

* Note: In order to run the tests easily, *GET* endpoints were created to produce a value.

### Build and Start Stack

To compile the project:

* ```gradle clean build```

    * ```-x test``` to build without test (faster)

To run the stack:

* Enter the folder: ```src/main/resources```

* Run docker-compose to start: ```docker-compose -f docker-compose.yaml up --build```

### Test Kafka

* Produce some values to Kafka, for instance:

    * [http://localhost:8080/api/v1/kafka/message/1](http://localhost:8080/api/v1/kafka/message/1)
    
    * [http://localhost:8080/api/v1/kafka/message/2](http://localhost:8080/api/v1/kafka/message/2)
    
    * [http://localhost:8080/api/v1/kafka/message/3](http://localhost:8080/api/v1/kafka/message/3)

* [http://localhost:8080/api/v1/kafka/messages](http://localhost:8080/api/v1/kafka/messages): list messages that were produced and consumed by Kafka

* [http://localhost:19000](http://localhost:19000): access KafkaDrop

![kafka-1](https://i.imgur.com/GG9PJBS.png)

![kafka-2](https://i.imgur.com/9z5fAeX.png)

![kafka-3](https://i.imgur.com/H7FDYsB.png)

### Test RabbitMQ

* Produce some values to RabbitMQ, for instance:

    * [http://localhost:8080/api/v1/rabbit/message/10](http://localhost:8080/api/v1/rabbit/message/10)
    
    * [http://localhost:8080/api/v1/rabbit/message/11](http://localhost:8080/api/v1/rabbit/message/11)
    
    * [http://localhost:8080/api/v1/rabbit/message/12](http://localhost:8080/api/v1/rabbit/message/12)
    
* [http://localhost:8080/api/v1/rabbit/messages](http://localhost:8080/api/v1/rabbit/messages): list messages that were produced and consumed by RabbitMQ

* [http://localhost:15672](http://localhost:15672): access RabbitMQ Management (guest / guest)

![rabbit-1](https://i.imgur.com/jDd2GtZ.png)

![rabbit-2](https://i.imgur.com/UVqPOWX.png)

### Test Spring WebFlux

* Produce some Kafka or RabbitMQ value 

* [http://localhost:8080/api/v1/reactive/kafka/messages](http://localhost:8080/api/v1/reactive/kafka/messages): endpoint that reuses Kafka list service using ```Mono``` 

* [http://localhost:8080/api/v1/reactive/rabbit/messages](http://localhost:8080/api/v1/reactive/rabbit/messages): endpoint that reuses Rabbit list service using ```Flux```

### Test Redis

* Run steps to produce values for Kafka

* Enter in CLI of Redis container: ```docker exec -it redis redis-cli```

* Please refer to [https://redis.io/commands](https://redis.io/commands). E.g.: ```KEYS *```

### Test MongoDB

* Run steps to produce values for Mongo

* Enter in CLI of Mongo container: ```docker exec -it mongo mongo```

* Please refer to [https://docs.mongodb.com/manual/reference/mongo-shell/](https://docs.mongodb.com/manual/reference/mongo-shell/). E.g.: ```db.message.find()```

### API Specification

* It's in [Swagger 3](https://swagger.io/specification/v3/) generated by *[Spring Open API UI](https://www.baeldung.com/spring-rest-openapi-documentation)*.

* To see API Specification:

    * Build and start stack to see the API specification

    * Access here: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


## Future Work

* Authentication

* Security

* More of Spring WebFlux


## References

* [REST Best Practices](https://codeburst.io/spring-boot-rest-microservices-best-practices-2a6e50797115)

