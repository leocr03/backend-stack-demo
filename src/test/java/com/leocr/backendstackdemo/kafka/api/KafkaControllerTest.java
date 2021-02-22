package com.leocr.backendstackdemo.kafka.api;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaControllerTest {

    private KafkaController controller;

    @Mock
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        controller = new KafkaController(kafkaService);
    }

    @Test
    void kafkaProduce() {
        final int someNumber = 5;
        controller.produce(someNumber);
        verify(kafkaService).produce(eq(someNumber));
    }

    @Test
    void kafkaList() {
        final String value = "1, 2, 3, 4, 5";
        when(kafkaService.list()).thenReturn(value);

        final String result = controller.list();

        assertEquals("1, 2, 3, 4, 5", result);
        verify(kafkaService).list();
    }
}