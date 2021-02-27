package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.RabbitResponse;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitControllerTest {

    private RabbitController controller;

    @Mock
    private RabbitService rabbitService;

    @BeforeEach
    void setUp() {
        controller = new RabbitController(rabbitService);
    }

    @Test
    void produce() {
        final Integer value = 2;
        controller.produce(value);
        verify(rabbitService).produce(anyInt());
    }

    @Test
    void list() {
        when(rabbitService.list()).thenReturn("someResult");

        final RabbitResponse list = controller.list();

        verify(rabbitService).list();
        final RabbitResponse expected = new RabbitResponse("someResult");
        assertEquals(expected, list);
    }
}