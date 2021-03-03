package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
public class RabbitPageDto extends BasicPageDto {

    public RabbitPageDto(Set<String> values) {
        super(values);
    }
}
