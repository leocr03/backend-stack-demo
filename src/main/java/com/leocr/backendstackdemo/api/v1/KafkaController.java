package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
public class KafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public KafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping(value = "/message/{someNumber}", produces = "application/json")
    public void produce(@PathVariable Integer someNumber) {
        kafkaService.produce(someNumber);
    }

    @GetMapping(value = "/messages", produces = "application/json")
    public String list() {
        return kafkaService.list();
    }
}
