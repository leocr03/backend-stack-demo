package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class RabbitDto extends BasicDto {

    public RabbitDto(String value, String message) {
        super(value, message);
    }
}
