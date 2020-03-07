package com.shpl.flightbooking.ITtests;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.shpl.flightbooking.FlightbookingApplication;
import com.shpl.flightbooking.config.AwsDynamoDBTestConfig;
import com.shpl.flightbooking.controller.FlightController;
import com.shpl.flightbooking.dto.FlightInfoResponseDto;
import com.shpl.flightbooking.dto.FlightPushDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest(classes = {FlightController.class, FlightbookingApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AwsDynamoDBTestConfig.class)
@AutoConfigureWebTestClient
public class FlightControllerITTest {

    @Autowired
    AmazonDynamoDB dynamoDB;
    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp() {
        AwsDynamoDBTestConfig.loadTables(dynamoDB);
    }

    @After
    public void tearDown() {
        AwsDynamoDBTestConfig.resetTables(dynamoDB);
    }

    @Test
    public void shouldCreateFlight() {
        this.webTestClient
                .post()
                .uri("/flight/pushFlight")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(FlightControllerData.testFlight), FlightPushDto.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void shouldGetFlight() {

        this.webTestClient
                .post()
                .uri("/flight/pushFlight")
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(FlightControllerData.testFlight), FlightPushDto.class)
                .exchange()
                .expectStatus()
                .isCreated();

        FlightInfoResponseDto responseBody = this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flight/getFlight")
                        .queryParam("id", FlightControllerData.testFlight.getId())
                        .queryParam("iataCode", FlightControllerData.testFlight.getIataCode())
                        .build())
                .exchange()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();
        
        assertThat(responseBody.getId()).isEqualTo(FlightControllerData.testFlight.getId());


    }

    @Test
    public void shouldGetNullObjectWhenUnexistingFlight() {
        FlightInfoResponseDto responseBody = this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flight/getFlight")
                        .queryParam("id", "nooooo")
                        .queryParam("iataCode", "reerterter")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getId()).isNull();
        assertThat(responseBody.getIataCode()).isNull();
        assertThat(responseBody.getSoldSeats()).isEqualTo(0);
        assertThat(responseBody.getTotalSeatsAvailable()).isEqualTo(0);
    }

    @Test
    public void shouldGet404WhenWrongEndpoint() {
        this.webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/flight/getFlightertt")
                        .queryParam("id", FlightControllerData.testFlight.getId())
                        .queryParam("iataCode", FlightControllerData.testFlight.getIataCode())
                        .build())
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
