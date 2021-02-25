package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public RabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @GetMapping(value = "/message/{someNumber}", produces = "application/json")
    public void produce(@PathVariable Integer someNumber) {
        rabbitService.produce(someNumber);
    }

    @GetMapping(value = "/messages", produces = "application/json")
    public String list() {
        return rabbitService.list();
    }
}
