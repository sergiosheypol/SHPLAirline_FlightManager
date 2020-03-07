package com.shpl.flightbooking.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.shpl.flightbooking.config.AwsDynamoProperties;
import com.shpl.flightbooking.dto.FlightKeysDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import com.shpl.flightbooking.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@EnableConfigurationProperties(AwsDynamoProperties.class)
public class FlightRepository {

    private final AwsDynamoProperties awsDynamoProperties;

    private final AmazonDynamoDBAsync dynamoDB;

    private final DynamoDBMapper mapper;

    @Value("${aircraft.passengers}")
    private String nPassengers;

    public void save(FlightPushDto flightPushDto) {
        dynamoDB.putItemAsync(awsDynamoProperties.getTableName(), generateAttributesMap(flightPushDto));
    }

    public Mono<Flight> findFlight(FlightKeysDto flightKeysDto) {
        return Mono.fromCallable(() -> mapper.load(Flight.class, flightKeysDto.getId(), flightKeysDto.getIataCode()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Map<String, AttributeValue> generateAttributesMap(FlightPushDto flightPushDto) {
        HashMap<String, AttributeValue> map = new HashMap<>();
        map.put("id", new AttributeValue(flightPushDto.getId()));
        map.put("iataCode", new AttributeValue(flightPushDto.getIataCode()));
        map.put("departureAirport", new AttributeValue(flightPushDto.getDepartureAirport()));
        map.put("arrivalAirport", new AttributeValue(flightPushDto.getArrivalAirport()));
        map.put("connectingAirport", new AttributeValue(flightPushDto.getConnectingAirport()));
        map.put("departureDate", new AttributeValue(flightPushDto.getDepartureDate().toString()));
        map.put("arrivalDate", new AttributeValue(flightPushDto.getArrivalDate().toString()));
        map.put("totalSeatsAvailable", new AttributeValue().withN(nPassengers));
        map.put("soldSeats", new AttributeValue().withN("0"));
        map.put("passengersPnr", new AttributeValue().withSS("ER45TY"));
        return map;
    }
}
