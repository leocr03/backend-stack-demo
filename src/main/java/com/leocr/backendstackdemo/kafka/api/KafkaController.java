package com.leocr.backendstackdemo.kafka.api;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {     

    @GetMapping(value = "/kafka/{someNumber}", produces = "application/json")
    public ResponseEntity<String> kafka(@PathVariable int someNumber) {
        return new ResponseEntity<>(String.valueOf(someNumber), HttpStatus.OK);
    }
}
