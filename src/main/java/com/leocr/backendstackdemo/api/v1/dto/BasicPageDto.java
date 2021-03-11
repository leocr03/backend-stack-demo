package com.leocr.backendstackdemo.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BasicPageDto {

    @NotNull
    private Set<String> values;
}
