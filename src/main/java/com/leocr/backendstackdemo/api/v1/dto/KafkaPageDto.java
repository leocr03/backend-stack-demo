package com.leocr.backendstackdemo.api.v1.dto;

import lombok.*;

import java.util.Set;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class KafkaPageDto extends BasicPageDto {

    public KafkaPageDto(Set<String> values) {
        super(values);
    }
}
