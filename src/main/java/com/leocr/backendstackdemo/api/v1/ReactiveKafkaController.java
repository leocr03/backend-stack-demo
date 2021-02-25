package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive/kafka")
public class ReactiveKafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public ReactiveKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping(value = "/messages", produces = "application/json")
    public Mono<String> list() {
        return Mono.just(kafkaService.list());
    }
}
