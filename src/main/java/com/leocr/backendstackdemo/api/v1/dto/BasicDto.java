package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public abstract class BasicDto {

    @Getter
    protected final String value;

    @Getter
    protected final String message;

    public BasicDto(String value, String message) {
        this.value = value;
        this.message = message;
    }
}
