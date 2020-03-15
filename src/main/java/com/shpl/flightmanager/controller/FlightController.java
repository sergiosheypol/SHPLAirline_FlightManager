package com.shpl.flightmanager.controller;

import com.shpl.flightmanager.dto.FlightBookingResultDto;
import com.shpl.flightmanager.dto.FlightExistsDto;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeatsDto;
import com.shpl.flightmanager.facade.FlightFacade;
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

    private final FlightFacade flightFacade;

    @PostMapping("/pushFlight")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Mono<FlightInfoResponseDto> pushFlight(@Valid @RequestBody FlightPushDto flightPushDto) {
        return flightFacade.saveFlight(flightPushDto);
    }

    @PostMapping("/saveNewBooking/{flightId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Mono<FlightBookingResultDto> saveNewBooking(@PathVariable("flightId") final String flightId) {
        return flightFacade.saveNewBooking(flightId);
    }

    @GetMapping(value = "/getFlight/{flightId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<FlightInfoResponseDto> getFlight(@PathVariable("flightId") final String flightId) {
        return flightFacade.findFlight(flightId);
    }

    @PostMapping("/deleteFlight/{flightId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightInfoResponseDto> deleteFlight(@PathVariable("flightId") final String flightId) {
        return flightFacade.deleteFlight(flightId);
    }

    @GetMapping("/availableSeats/{flightId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightRemainingSeatsDto> getBookingInfo(@PathVariable("flightId") final String flightId) {
        return flightFacade.getBookingInfo(flightId);
    }

    @GetMapping("/isFlightAvailable/{flightId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Mono<FlightExistsDto> isFlightAvailable(@PathVariable("flightId") final String flightId) {
        return flightFacade.isFlightAvailable(flightId);
    }


}
