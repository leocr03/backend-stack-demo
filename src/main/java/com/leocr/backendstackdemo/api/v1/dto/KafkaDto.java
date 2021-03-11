package com.leocr.backendstackdemo.api.v1.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KafkaDto extends BasicDto {

    public KafkaDto(@Valid @NotEmpty @NotBlank @NotNull @Size(max = 5, message = "The value must have equal or less than 5 digits") String value, @Valid @NotEmpty @NotBlank @NotNull @Size(max = 10000, message = "The value must have equal or less than 10000 characters") String message) {
        super(value, message);
    }
}
