package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.controller.ReactiveBrokerController;
import com.leocr.backendstackdemo.api.v1.controller.ReactiveRabbitController;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import com.leocr.backendstackdemo.common.service.BrokerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static java.util.stream.Collectors.toSet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReactiveRabbitControllerTest {

    private ReactiveBrokerController controller;

    @Mock
    private BrokerService service;

    @BeforeEach
    void setUp() {
        controller = new ReactiveRabbitController(service);
    }

    @Test
    void list() {
        when(service.list()).thenReturn(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet()));
        final Flux<BrokerPageDto> list = (Flux<BrokerPageDto>) controller.list();
        StepVerifier.create(list)
                .expectNext(new BrokerPageDto(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet())))
                .expectComplete()
                .verify();
    }
}