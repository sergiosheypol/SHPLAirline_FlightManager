package com.shpl.flightmanager.controller;

import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightKeysDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeats;
import com.shpl.flightmanager.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final FlightService flightService;

    @PostMapping("/pushFlight")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Mono<FlightInfoResponseDto> pushFlight(@Valid @RequestBody FlightPushDto flightPushDto) {
        return flightService.saveFlight(flightPushDto);
    }

    @PostMapping("/updateFlight")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Mono<FlightInfoResponseDto> updateFlight(@Valid @RequestBody FlightPushDto flightPushDto) {
        return flightService.saveFlight(flightPushDto);
    }

    @GetMapping(value = "/getFlight", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<FlightInfoResponseDto> getFlight(@Valid final FlightKeysDto keys) {
        return flightService.findFlight(keys);
    }

    @PostMapping("/deleteFlight")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightInfoResponseDto> deleteFlight(@Valid @RequestBody final FlightKeysDto keys) {
        return flightService.deleteFlight(keys);
    }

    @GetMapping("/availableSeats/{iataCode}/{flightId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightRemainingSeats> getBookingInfo(@PathVariable("iataCode") final String iataCode,
                                                     @PathVariable("flightId") final String flightId) {
        return flightService.getBookingInfo(FlightKeysDto.builder().iataCode(iataCode).id(flightId).build());
    }


}
