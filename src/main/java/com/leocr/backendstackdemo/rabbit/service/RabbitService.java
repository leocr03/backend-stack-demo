package com.leocr.backendstackdemo.rabbit.service;

import com.leocr.backendstackdemo.rabbit.conf.RabbitConfig;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.redis.repo.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RabbitService {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitConfig rabbitConfig;

    private final MessageRepository messageRepository;

    @Autowired
    public RabbitService(RabbitTemplate rabbitTemplate, RabbitConfig rabbitConfig,
                         MessageRepository messageRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitConfig = rabbitConfig;
        this.messageRepository = messageRepository;
    }

    public void produce(Integer value) {
        final String message = value.toString();
        final String routingKey = rabbitConfig.getRoutingKey();
        final String exchange = rabbitConfig.getExchange();
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    @Qualifier("receiveMessage")
    @RabbitListener(queues = "#{rabbitConfig.getQueue()}")
    public void consume(String message) {
        System.out.printf("[RabbitMQ][%s][%s] Received Message: " + message + "%n",  rabbitConfig.getExchange(),
                rabbitConfig.getRoutingKey());
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        messageRepository.save(msg);
        System.out.println("[RabbitMQ] Message saved on Redis: " + message);
    }

    public String list() {
        final Iterable<Message> iterable = messageRepository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}
