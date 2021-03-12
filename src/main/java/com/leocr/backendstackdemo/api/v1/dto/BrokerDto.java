package com.leocr.backendstackdemo.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokerDto {

    @Valid
    @NotEmpty
    @NotBlank
    @Size(max = 5, message = "The value must have equal or less than 5 digits")
    protected String value;

    @Valid
    @NotEmpty
    @NotBlank
    @Size(max = 10000, message = "The value must have equal or less than 10000 characters")
    protected String message;
}
