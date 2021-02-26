package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.RabbitResponse;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

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
        when(rabbitService.list()).thenReturn("1, 2");
        final Flux<RabbitResponse> list = controller.list();
        StepVerifier.create(list)
                .expectNext(new RabbitResponse("1, 2"))
                .expectComplete()
                .verify();
    }
}