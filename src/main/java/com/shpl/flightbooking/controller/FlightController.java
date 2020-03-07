package com.shpl.flightbooking.controller;

import com.shpl.flightbooking.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.dto.FlightKeysDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import com.shpl.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/pushFlight")
    @ResponseStatus(HttpStatus.CREATED)
    public void pushFlight(@RequestBody FlightPushDto flightPushDto) {
        flightService.saveFlight(flightPushDto);
    }

    @GetMapping(value = "/getFlight", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //TODO: add @Valid annotation
    public Mono<FlightInfoResponseDto> getFlight(final FlightKeysDto keys) {
        return flightService.findFlight(keys);
    }
}
