package com.shpl.flightbooking.flight.controller;

import com.shpl.flightbooking.flight.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.flight.dto.FlightKeysDto;
import com.shpl.flightbooking.flight.dto.FlightPushDto;
import com.shpl.flightbooking.flight.service.FlightCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final FlightCrudService flightCrudService;

    @PostMapping("/pushFlight")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Mono<FlightInfoResponseDto> pushFlight(@Valid @RequestBody FlightPushDto flightPushDto) {
        return flightCrudService.saveFlight(flightPushDto);
    }

    @PostMapping("/updateFlight")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Mono<FlightInfoResponseDto> updateFlight(@Valid @RequestBody FlightPushDto flightPushDto) {
        return flightCrudService.saveFlight(flightPushDto);
    }

    @GetMapping(value = "/getFlight", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<FlightInfoResponseDto> getFlight(@Valid final FlightKeysDto keys) {
        return flightCrudService.findFlight(keys);
    }

    @PostMapping("/deleteFlight")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightInfoResponseDto> deleteFlight(@Valid @RequestBody final FlightKeysDto keys) {
        return flightCrudService.deleteFlight(keys);
    }

}
