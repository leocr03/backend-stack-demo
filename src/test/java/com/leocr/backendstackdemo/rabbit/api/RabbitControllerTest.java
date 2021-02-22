package com.leocr.backendstackdemo.rabbit.api;

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
        final Integer someNumber = 2;
        controller.produce(someNumber);
        verify(rabbitService).produce(anyInt());
    }

    @Test
    void list() {
        when(rabbitService.list()).thenReturn("someResult");

        final String list = controller.list();

        verify(rabbitService).list();
        assertEquals("someResult", list);
    }
}