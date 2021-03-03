package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class RabbitPageDto extends BasicPageDto {

    public RabbitPageDto(List<String> values) {
        super(values);
    }
}
