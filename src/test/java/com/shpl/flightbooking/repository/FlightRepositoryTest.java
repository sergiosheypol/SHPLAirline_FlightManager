package com.shpl.flightbooking.repository;

import com.shpl.flightbooking.config.AwsDynamoDBConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AwsDynamoDBConfig.class)
public class FlightRepositoryTest {
    @InjectMocks
    FlightRepository flightRepository;

    @Test
    public void doNothing() {

    }


}
