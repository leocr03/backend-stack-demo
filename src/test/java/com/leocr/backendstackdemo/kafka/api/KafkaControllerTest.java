package com.leocr.backendstackdemo.kafka.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaControllerTest {

    private KafkaController controller;

    @BeforeEach
    void setUp() {
        controller = new KafkaController();
    }

    @Test
    void kafkaProduce() {
        final int someNumber = 5;
        final ResponseEntity<String> result = controller.kafkaProduce(someNumber);
        assertEquals(new ResponseEntity<>(String.valueOf(someNumber), HttpStatus.OK), result);
    }

    @Test
    void kafkaConsume() {
        final int someNumber = 5;
        final ResponseEntity<String> result = controller.kafkaProduce(someNumber);
        assertEquals(new ResponseEntity<>(String.valueOf(someNumber), HttpStatus.OK), result);
    }
}