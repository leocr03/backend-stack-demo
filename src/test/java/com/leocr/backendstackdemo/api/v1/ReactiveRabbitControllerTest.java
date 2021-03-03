package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.RabbitPageDto;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
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

    private ReactiveRabbitController controller;

    @Mock
    private RabbitService rabbitService;

    @BeforeEach
    void setUp() {
        controller = new ReactiveRabbitController(rabbitService);
    }

    @Test
    void list() {
        when(rabbitService.list()).thenReturn(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet()));
        final Flux<RabbitPageDto> list = controller.list();
        StepVerifier.create(list)
                .expectNext(new RabbitPageDto(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet())))
                .expectComplete()
                .verify();
    }
}