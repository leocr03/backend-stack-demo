package com.leocr.backendstackdemo.api.v1.controller;

import com.leocr.backendstackdemo.api.v1.dto.BrokerDto;
import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import org.springframework.http.ResponseEntity;

public interface BrokerController {

    ResponseEntity<BrokerDto> produce(Integer value);

    ResponseEntity<BrokerPageDto> list();
}
