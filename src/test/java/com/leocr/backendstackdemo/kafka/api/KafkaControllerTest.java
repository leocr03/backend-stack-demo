package com.leocr.backendstackdemo.kafka.api;

import com.leocr.backendstackdemo.kafka.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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
        final ListenableFuture<SendResult<String, String>> listener = mock(ListenableFuture.class);
        when(kafkaService.produce(anyInt())).thenReturn(listener);

        controller.kafkaProduce(someNumber);

        verify(kafkaService).produce(eq(someNumber));
    }
}