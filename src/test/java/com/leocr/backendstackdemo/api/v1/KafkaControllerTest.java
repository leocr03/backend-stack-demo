package com.leocr.backendstackdemo.api.v1;

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
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaControllerTest {

    private KafkaController controller;

    @Mock
    private BrokerService service;

    @BeforeEach
    void setUp() {
        controller = new KafkaController(service);
    }

    @Test
    void kafkaProduce() {
        final Integer value = 5;
        when(service.produce(any())).thenReturn("5");

        final ResponseEntity<BrokerDto> response = controller.produce(value);

        verify(service).produce(eq(value));
        final BrokerDto expected = new BrokerDto("5", "Value produced to Kafka: 5");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        final BrokerDto dto = response.getBody();
        assertNotNull(dto);
        assertNotNull(dto.getValue());
        assertNotNull(dto.getMessage());
        assertEquals(expected.getValue(), dto.getValue());
        assertEquals(expected.getMessage(), dto.getMessage());
    }

    @Test
    void kafkaList() {
        final Set<String> values = Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet());
        when(service.list()).thenReturn(values);

        final ResponseEntity<BrokerPageDto> result = controller.list();

        final BrokerPageDto dto = new BrokerPageDto(Arrays.stream(new String[]{"1", "2", "3", "4", "5"}).collect(toSet()));
        final ResponseEntity<BrokerPageDto> expected = new ResponseEntity<>(dto, HttpStatus.OK);
        assertEquals(expected, result);
        verify(service).list();
    }
}