package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.controller.BrokerController;
import com.leocr.backendstackdemo.api.v1.controller.RabbitController;
import com.leocr.backendstackdemo.api.v1.dto.BrokerDto;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import com.leocr.backendstackdemo.common.service.BrokerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitControllerTest {

    private BrokerController controller;

    @Mock
    private BrokerService service;

    @BeforeEach
    void setUp() {
        controller = new RabbitController(service);
    }

    @Test
    void produce() {
        final Integer value = 2;
        when(service.produce(any())).thenReturn("5");

        final ResponseEntity<BrokerDto> response = controller.produce(value);

        verify(service).produce(eq(value));
        final BrokerDto expected = new BrokerDto("5", "Value produced to RabbitMQ: 2");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final BrokerDto dto = response.getBody();
        assertNotNull(dto);
        assertNotNull(dto.getValue());
        assertNotNull(dto.getMessage());
        assertEquals(expected.getValue(), dto.getValue());
        assertEquals(expected.getMessage(), dto.getMessage());
    }

    @Test
    void list() {
        when(service.list()).thenReturn(Arrays.stream(new String[]{"someResult"}).collect(toSet()));

        final ResponseEntity<BrokerPageDto> response = controller.list();

        verify(service).list();
        final BrokerPageDto dto = new BrokerPageDto(Arrays.stream(new String[]{"someResult"}).collect(toSet()));
        final ResponseEntity<BrokerPageDto> expected = new ResponseEntity<>(
                dto, HttpStatus.OK);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected.getBody(), response.getBody());
    }
}