package com.leocr.backendstackdemo.rabbit.api;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public RabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @GetMapping(value = "/rabbit/produce/{someNumber}", produces = "application/json")
    public void produce(@PathVariable Integer someNumber) {
        rabbitService.produce(someNumber);
    }

    @GetMapping(value = "/rabbit/list", produces = "application/json")
    public String list() {
        return rabbitService.list();
    }
}
