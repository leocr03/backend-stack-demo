package com.leocr.backendstackdemo.api.v1.model;

import lombok.Data;

@Data
public class RabbitResponse {

    private String value;

    public RabbitResponse(String value) {
        this.value = value;
    }
}
