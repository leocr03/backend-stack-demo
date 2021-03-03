package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.dto.RabbitDto;
import com.leocr.backendstackdemo.api.v1.dto.RabbitPageDto;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
        when(rabbitService.produce(any())).thenReturn("5");

        final ResponseEntity<RabbitDto> response = controller.produce(value);

        verify(rabbitService).produce(eq(value));
        final RabbitDto expected = new RabbitDto("5", "Value produced to RabbitMQ: 2");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final RabbitDto dto = response.getBody();
        assertNotNull(dto);
        assertNotNull(dto.getValue());
        assertNotNull(dto.getMessage());
        assertEquals(expected.getValue(), dto.getValue());
        assertEquals(expected.getMessage(), dto.getMessage());
    }

    @Test
    void list() {
        when(rabbitService.list()).thenReturn(Collections.singletonList("someResult"));

        final ResponseEntity<RabbitPageDto> response = controller.list();

        verify(rabbitService).list();
        final RabbitPageDto dto = new RabbitPageDto(Collections.singletonList("someResult"));
        final ResponseEntity<RabbitPageDto> expected = new ResponseEntity<>(
                dto, HttpStatus.OK);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}