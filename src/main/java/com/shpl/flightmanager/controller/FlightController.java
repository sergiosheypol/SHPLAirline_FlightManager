package com.shpl.flightmanager.controller;

import com.shpl.flightmanager.dto.*;
import com.shpl.flightmanager.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/saveNewBooking")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Mono<FlightBookingResult> saveNewBooking(@Valid @RequestBody FlightKeysDto flightKeysDto) {
        return flightService.saveNewBooking(flightKeysDto);
    }

    @GetMapping(value = "/getFlight/{iataCode}/{flightId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<FlightInfoResponseDto> getFlight(@PathVariable("iataCode") final String iataCode,
                                                 @PathVariable("flightId") final String flightId) {
        return flightService.findFlight(FlightKeysDto.builder().iataCode(iataCode).id(flightId).build());
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

    @GetMapping("/isFlightAvailable/{iataCode}/{flightId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightExistsDto> isFlightAvailable(@PathVariable("iataCode") final String iataCode,
                                                   @PathVariable("flightId") final String flightId) {
        return flightService.isFlightAvailable(FlightKeysDto.builder().iataCode(iataCode).id(flightId).build());
    }


}
