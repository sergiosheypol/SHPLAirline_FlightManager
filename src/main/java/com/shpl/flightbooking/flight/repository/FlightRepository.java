package com.shpl.flightbooking.flight.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.shpl.flightbooking.flight.dto.FlightKeysDto;
import com.shpl.flightbooking.flight.entity.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
@RequiredArgsConstructor
public class FlightRepository {

    private final DynamoDBMapper mapper;

    public Mono<Flight> saveOrUpdate(Flight flight) {
        return Mono.fromCallable(() -> {
            mapper.save(flight);
            return flight;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> find(FlightKeysDto flightKeysDto) {
        return Mono.fromCallable(() -> mapper.load(Flight.class, flightKeysDto.getId(), flightKeysDto.getIataCode()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Flight> delete(FlightKeysDto flightKeysDto) {
        return find(flightKeysDto).map(flight -> {
            mapper.delete(flight);
            return flight;
        });
    }
}
