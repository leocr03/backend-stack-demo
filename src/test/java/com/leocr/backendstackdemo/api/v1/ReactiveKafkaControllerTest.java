package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import com.leocr.backendstackdemo.common.service.BrokerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveKafkaControllerTest {

    private ReactiveKafkaController controller;

    @Mock
    private BrokerService service;

    @BeforeEach
    void setUp() {
        controller = new ReactiveKafkaController(service);
    }

    @Test
    void list() {
        final Set<String> values = Arrays.stream(new String[]{"1", "2", "3"}).collect(toSet());
        when(service.list()).thenReturn(values);
        final Mono<BrokerPageDto> list = controller.list();
        StepVerifier.create(list)
                .expectNext(new BrokerPageDto(Arrays.stream(new String[]{"1", "2", "3"}).collect(toSet())))
                .expectComplete()
                .verify();
    }
}