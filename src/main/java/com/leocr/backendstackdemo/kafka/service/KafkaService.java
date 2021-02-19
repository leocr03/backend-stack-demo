package com.leocr.backendstackdemo.kafka.service;

import com.leocr.backendstackdemo.kafka.conf.KafkaTopicConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private final KafkaTopicConfig kafkaTopicConfig;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, KafkaTopicConfig kafkaTopicConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicConfig = kafkaTopicConfig;
    }

    public ListenableFuture<SendResult<String, String>> produce(Integer value) {
        final String topicName = kafkaTopicConfig.getTopicName();
        return kafkaTemplate.send(topicName, String.valueOf(value));
    }

    @KafkaListener(topics = "#{kafkaTopicConfig.getTopicName()}", groupId = "#{kafkaTopicConfig.getGroupId()}")
    public String consume(String message) {
        System.out.printf("[%s][%s] Received Message: " + message + "%n",  kafkaTopicConfig.getGroupId(),
                kafkaTopicConfig.getTopicName());
        return message;
    }
}
