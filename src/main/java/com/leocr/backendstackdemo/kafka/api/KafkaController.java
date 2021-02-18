package com.leocr.backendstackdemo.kafka.api;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping(value = "/kafka/produce/{someNumber}", produces = "application/json")
    public ResponseEntity<String> kafkaProduce(@PathVariable Integer someNumber) {
        kafkaService.produce(someNumber).addCallback(result -> new Runnable() {
            @Override
            public void run() {
                System.out.println("Result: " + result);
            }
        }, ex -> {});
        return new ResponseEntity<>(String.valueOf(someNumber), HttpStatus.OK);
    }
}
