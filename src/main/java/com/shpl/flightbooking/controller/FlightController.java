package com.shpl.flightbooking.controller;


import com.shpl.flightbooking.dto.FlightDto;
import com.shpl.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/flight")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/pushFlight")
    @ResponseStatus(HttpStatus.CREATED)
    public void pushFlight(FlightDto flightDto) {
        flightService.saveFlight(flightDto);
    }

    @GetMapping("/getFlight/{id}")
    public FlightDto getFlight(@PathVariable("id") final String id) {

        return flightService.getFlight(id);
    }
}
