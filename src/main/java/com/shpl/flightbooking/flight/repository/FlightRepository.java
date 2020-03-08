package com.shpl.flightbooking.flight.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shpl.flightbooking.flight.dto.FlightKeysDto;
import com.shpl.flightbooking.flight.dto.FlightPushDto;
import com.shpl.flightbooking.flight.mapper.FlightMapper;
import com.shpl.flightbooking.flight.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class FlightRepository {

    private final DynamoDBMapper mapper;

    private final FlightMapper flightMapper;

    public Mono<Flight> save(FlightPushDto flightPushDto) {
        return Mono.fromCallable(() -> flightMapper.flightPushDtoToFlight(flightPushDto))
                .map(flight -> {
                    mapper.save(flight);
                    return flight;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> findFlight(FlightKeysDto flightKeysDto) {
        return Mono.fromCallable(() -> mapper.load(Flight.class, flightKeysDto.getId(), flightKeysDto.getIataCode()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> deleteFlight(FlightKeysDto flightKeysDto) {
        return findFlight(flightKeysDto).map(flight -> {
            mapper.delete(flight);
            return flight;
        });
    }


}
