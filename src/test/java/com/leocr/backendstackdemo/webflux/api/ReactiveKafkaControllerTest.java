package com.leocr.backendstackdemo.webflux.api;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveKafkaControllerTest {

    private ReactiveKafkaController controller;

    @Mock
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        controller = new ReactiveKafkaController(kafkaService);
    }

    @Test
    void list() {
        when(kafkaService.list()).thenReturn("1, 2, 3");
        final Mono<String> list = controller.list();
        StepVerifier.create(list)
                .expectNext("1, 2, 3")
                .expectComplete()
                .verify();
    }
}