package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.KafkaResponse;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

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
        final Mono<KafkaResponse> list = controller.list();
        StepVerifier.create(list)
                .expectNext(new KafkaResponse("1, 2, 3"))
                .expectComplete()
                .verify();
    }
}