package com.leocr.backendstackdemo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaDto extends BasicDto {

    @SuppressWarnings("unused")
    public KafkaDto() {
        super(null, null);
    }

    public KafkaDto(String valueProduced, String message) {
        super(valueProduced, message);
    }
}
