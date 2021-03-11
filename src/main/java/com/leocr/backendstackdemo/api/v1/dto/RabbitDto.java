package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RabbitDto extends BasicDto {

    public RabbitDto(String value, String message) {
        super(value, message);
    }
}
