package com.leocr.backendstackdemo.api.v1.controller;

import com.leocr.backendstackdemo.api.v1.dto.BrokerDto;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import org.hibernate.validator.constraints.Range;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

public interface BrokerController {

    @NotNull ResponseEntity<BrokerDto> produce(@Range(min = 1, max = 99999) @NotNull Integer value);

    @NotNull ResponseEntity<BrokerPageDto> list();
}
