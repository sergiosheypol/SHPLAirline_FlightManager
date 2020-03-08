package com.shpl.flightbooking.flight.service;

import com.shpl.flightbooking.flight.dto.FlightDto;
import com.shpl.flightbooking.flight.dto.FlightNewBookingConfirmedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurseNewBookingService {

    private final FlightCrudService flightCrudService;

    public FlightNewBookingConfirmedDto curseNewBooking(FlightDto flightDto) {
        //TODO process flightDto


        flightCrudService.updateNewBooking(flightDto);


        return FlightNewBookingConfirmedDto.builder().build();
    }
}
