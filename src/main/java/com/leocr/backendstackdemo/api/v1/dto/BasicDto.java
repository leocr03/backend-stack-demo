package com.leocr.backendstackdemo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasicDto {

    @Valid
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 5, message = "The value must have equal or less than 5 digits")
    protected String value;

    @Valid
    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    @NotNull
    @Size(max = 10000, message = "The value must have equal or less than 10000 characters")
    protected String message;
}
