package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/reactive/rabbit")
public class ReactiveRabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public ReactiveRabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @GetMapping(value = "/messages", produces = "application/json")
    public Flux<String> list() {
        return Flux.just(rabbitService.list());
    }
}
