package com.leocr.backendstackdemo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@EqualsAndHashCode
public abstract class BasicPageDto {

    @Getter
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @NotNull
    protected final Set<String> values;

    protected BasicPageDto(Set<String> values) {
        this.values = values;
    }
}
