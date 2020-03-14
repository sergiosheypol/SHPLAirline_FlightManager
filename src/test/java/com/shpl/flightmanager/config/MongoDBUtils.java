package com.shpl.flightmanager.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.shpl.flightmanager.controller.FlightControllerData;
import com.shpl.flightmanager.mapper.FlightMapper;
import com.shpl.flightmanager.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBUtils {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoDBProperties mongoDBProperties;

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightMapper flightMapper;

    public void createCollection() {
        mongoClient
                .getDatabase(mongoDBProperties.getDatabaseName())
                .createCollection(mongoDBProperties.getFlightsCollectionName());
    }

    public void createTestFlight() {
        flightRepository.save(flightMapper.flightPushDtoToFlight(FlightControllerData.testFlight)).block();
    }

    public void createFullFlight() {
        flightRepository.save(flightMapper.flightDtoToFlight(FlightControllerData.fullFlight)).block();
    }

    public void deleteColletion() {
        mongoClient
                .getDatabase(mongoDBProperties.getDatabaseName())
                .getCollection(mongoDBProperties.getFlightsCollectionName())
                .drop();


    }

}
