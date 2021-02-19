package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import com.leocr.backendstackdemo.redis.model.Message;
import com.leocr.backendstackdemo.redis.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaTopicConfig kafkaTopicConfig;

    private MessageRepository messageRepository;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicConfig kafkaTopicConfig,
                        MessageRepository messageRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicConfig = kafkaTopicConfig;
        this.messageRepository = messageRepository;
    }

    public ListenableFuture<SendResult<String, String>> produce(Integer value) {
        final String topicName = kafkaTopicConfig.getTopicName();
        return kafkaTemplate.send(topicName, String.valueOf(value));
    }

    @KafkaListener(topics = "#{kafkaTopicConfig.getTopicName()}", groupId = "#{kafkaTopicConfig.getGroupId()}")
    public String consume(String message) {
        System.out.printf("[%s][%s] Received Message: " + message + "%n",  kafkaTopicConfig.getGroupId(),
                kafkaTopicConfig.getTopicName());
        final Integer value = Integer.valueOf(message);
        final Message msg = new Message(value);
        messageRepository.save(msg);
        System.out.println("Message saved on Redis: " + message);
        return message;
    }

    public String list() {
        Iterable<Message> iterable = messageRepository.findAll();
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.NONNULL), false)
                .sorted(Comparator.comparing(Message::getCreatedAt))
                .map(Message::getValue)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }
}