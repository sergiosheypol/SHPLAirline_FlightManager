package com.shpl.flightbooking.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.shpl.flightbooking.config.AwsDynamoProperties;
import com.shpl.flightbooking.dto.FlightKeysDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import com.shpl.flightbooking.model.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@EnableConfigurationProperties(AwsDynamoProperties.class)
public class FlightRepository {

    private final AwsDynamoProperties awsDynamoProperties;

    private final AmazonDynamoDB dynamoDB;

    @Value("${aircraft.passengers}")
    private String nPassengers;

    public void save(FlightPushDto flightPushDto) {
        dynamoDB.putItem(awsDynamoProperties.getTableName(), generateAttributesMap(flightPushDto));
    }

    public Flight findFlight(FlightKeysDto flightKeysDto) {
        return generateFlightFromMap(dynamoDB
                .getItem(awsDynamoProperties.getTableName(), getFlightKey(flightKeysDto.getId(), flightKeysDto.getIataCode()))
                .getItem());
    }

    private Map<String, AttributeValue> getFlightKey(String id, String iataCode) {
        Map<String, AttributeValue> keyMap = new HashMap<>();
        keyMap.put("id", new AttributeValue(id));
        keyMap.put("iataCode", new AttributeValue(iataCode));
        return keyMap;
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

    private Flight generateFlightFromMap(Map<String, AttributeValue> flightMap) {

        return Flight.builder()
                .id(flightMap.get("id").getS())
                .iataCode(flightMap.get("iataCode").getS())
                .departureAirport(flightMap.get("departureAirport").getS())
                .arrivalAirport(flightMap.get("arrivalAirport").getS())
                .connectingAirport(flightMap.get("connectingAirport").getS())
                .departureDate(LocalDateTime.parse(flightMap.get("departureDate").getS()))
                .arrivalDate(LocalDateTime.parse(flightMap.get("arrivalDate").getS()))
                .totalSeatsAvailable(Integer.parseInt(flightMap.get("totalSeatsAvailable").getN()))
                .soldSeats(Integer.parseInt(flightMap.get("soldSeats").getN()))
                .passengersPnr(flightMap.get("passengersPnr").getSS())
                .build();
    }



}
