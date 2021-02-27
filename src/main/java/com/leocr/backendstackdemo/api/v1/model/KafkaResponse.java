package com.leocr.backendstackdemo.api.v1.model;

import lombok.Data;

@Data
public class KafkaResponse {

    private String value;

    public KafkaResponse(String value) {
        this.value = value;
    }
}
