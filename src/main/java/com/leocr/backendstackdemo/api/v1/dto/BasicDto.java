package com.leocr.backendstackdemo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode
public abstract class BasicDto {

    @Getter
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 10, message = "The value must have equal or less than 10 digits")
    protected final String value;

    @Getter
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 10000, message = "The value must have equal or less than 10000 characters")
    protected final String message;

    public BasicDto(String value, String message) {
        this.value = value;
        this.message = message;
    }
}
