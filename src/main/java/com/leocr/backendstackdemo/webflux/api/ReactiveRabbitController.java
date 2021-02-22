package com.leocr.backendstackdemo.webflux.api;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ReactiveRabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public ReactiveRabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @GetMapping(value = "/reactive/rabbit/list", produces = "application/json")
    public Flux<String> list() {
        return Flux.just(rabbitService.list());
    }
}
