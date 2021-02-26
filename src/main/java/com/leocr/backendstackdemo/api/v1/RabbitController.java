package com.leocr.backendstackdemo.api.v1;

import com.leocr.backendstackdemo.api.v1.model.RabbitResponse;
import com.leocr.backendstackdemo.rabbit.service.RabbitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rabbit")
public class RabbitController {

    private final RabbitService rabbitService;

    @Autowired
    public RabbitController(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }

    @Operation(summary = "Produce value to RabbitMQ.", parameters = {
            @Parameter( name = "value", example = "11",
                        description = "Integer number to be produced on RabbitMQ.")
    })
    @GetMapping(value = "/message/{value}", produces = "application/json")
    public void produce(@PathVariable Integer value) {
        rabbitService.produce(value);
    }

    @Operation(summary = "List the messages produced and consumed in RabbitMQ.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Value that were produced and consumed by RabbitMQ.",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class))
                    })
    })
    @GetMapping(value = "/messages", produces = "application/json")
    public RabbitResponse list() {
        return new RabbitResponse(rabbitService.list());
    }
}
