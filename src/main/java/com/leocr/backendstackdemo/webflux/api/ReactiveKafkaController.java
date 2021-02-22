package com.leocr.backendstackdemo.webflux.api;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveKafkaController {

    private final KafkaService kafkaService;

    @Autowired
    public ReactiveKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @GetMapping(value = "/reactive/kafka/list", produces = "application/json")
    public Mono<String> list() {
        return Mono.just(kafkaService.list());
    }
}
