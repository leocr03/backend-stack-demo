package com.leocr.backendstackdemo.api.v1.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode
public abstract class BasicPageDto {

    @Getter
    protected final List<String> values;

    protected BasicPageDto(List<String> values) {
        this.values = values;
    }
}
