package com.leocr.backendstackdemo.rabbit.service;

import com.leocr.backendstackdemo.common.model.Message;
import com.leocr.backendstackdemo.mongo.repo.MongoMessageRepository;
import com.leocr.backendstackdemo.rabbit.conf.RabbitPropertiesConfig;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
@Slf4j
public class RabbitService {

    private static final String ARGUMENT_VALUE_CANNOT_BE_NULL = "Argument \"value\" cannot be null.";
    private static final String RABBIT_MQ_RECEIVED_MESSAGE = "[RabbitMQ][{}][{}] Received Message: {}}\n";
    private static final String RABBIT_MQ_MESSAGE_SAVED_ON_REDIS = "[RabbitMQ] Message saved on Redis: {}";

    private final RabbitTemplate rabbitTemplate;

    private final RabbitPropertiesConfig rabbitPropertiesConfig;

    private final MongoMessageRepository mongoMessageRepository;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate, RabbitPropertiesConfig rabbitPropertiesConfig,
                         MongoMessageRepository mongoMessageRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitPropertiesConfig = rabbitPropertiesConfig;
        this.mongoMessageRepository = mongoMessageRepository;
    }

    public String produce(@Nullable Integer value) {
        if (value != null) {
            final String message = value.toString();
            final String routingKey = rabbitPropertiesConfig.getRoutingKey();
            final String exchange = rabbitPropertiesConfig.getExchange();
            rabbitTemplate.convertAndSend(exchange, routingKey, message);
            return String.valueOf(value);
        } else {
            throw new IllegalArgumentException(ARGUMENT_VALUE_CANNOT_BE_NULL);
        }
    }

    @Qualifier("receiveMessage")
    @RabbitListener(queues = "#{rabbitPropertiesConfig.getQueue()}")
    public void consume(@NotNull String message) {
        log.info(RABBIT_MQ_RECEIVED_MESSAGE, rabbitPropertiesConfig.getExchange(),  rabbitPropertiesConfig.getRoutingKey(), message);
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        mongoMessageRepository.save(msg);
        log.info(RABBIT_MQ_MESSAGE_SAVED_ON_REDIS, message);
    }

    public Set<String> list() {
        final List<Message> messages = mongoMessageRepository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(messages.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(toSet());
    }
}
