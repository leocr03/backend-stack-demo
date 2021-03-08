package com.leocr.backendstackdemo.api.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KafkaPageDto extends BasicPageDto implements Serializable {

    private final static long serialVersionUID = 1L;

    @SuppressWarnings("unused")
    public KafkaPageDto() {
        super(new HashSet<>());
    }

    public KafkaPageDto(Set<String> values) {
        super(values);
    }
}
