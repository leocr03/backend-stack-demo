package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.KafkaPageDto;
import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

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
        final List<String> values = Arrays.asList("1", "2", "3");
        when(kafkaService.list()).thenReturn(values);
        final Mono<KafkaPageDto> list = controller.list();
        StepVerifier.create(list)
                .expectNext(new KafkaPageDto(Arrays.asList("1", "2", "3")))
                .expectComplete()
                .verify();
    }
}