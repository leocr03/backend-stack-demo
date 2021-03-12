package com.leocr.backendstackdemo.api.v1.controller;

import com.leocr.backendstackdemo.api.v1.dto.BrokerPageDto;
import org.jetbrains.annotations.NotNull;
import reactor.core.CorePublisher;

public interface ReactiveBrokerController {

    @NotNull CorePublisher<BrokerPageDto> list();
}
