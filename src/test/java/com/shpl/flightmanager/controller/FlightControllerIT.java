package com.shpl.flightmanager.controller;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.shpl.flightmanager.FlightbookingApplication;
import com.shpl.flightmanager.config.AwsDynamoDBTestConfig;
import com.shpl.flightmanager.config.AwsDynamoDBTestUtils;
import com.shpl.flightmanager.dto.*;
import com.shpl.flightmanager.service.PnrService;
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
public class FlightControllerIT {

    private final static String PUSH_ENDPOINT = "/flight/pushFlight";
    private final static String GET_ENDPOINT = "/flight/getFlight/{iataCode}/{flightId}";
    private final static String DELETE_ENDPOINT = "/flight/deleteFlight";
    private final static String AVAILABLE_SEATS_ENDPOINT = "/flight/availableSeats/{iataCode}/{flightId}";
    private final static String NEW_BOOKING_ENDPOINT = "/flight/saveNewBooking";

    @Autowired
    AmazonDynamoDB dynamoDB;
    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private AwsDynamoDBTestUtils awsDynamoDBTestUtils;

    @Autowired
    private PnrService pnrService;

    @Before
    public void setUp() {
        awsDynamoDBTestUtils.loadTables(dynamoDB);
        awsDynamoDBTestUtils.loadTestFlight(dynamoDB);
        awsDynamoDBTestUtils.loadFullFlight(dynamoDB);
        awsDynamoDBTestUtils.loadFlightTest2(dynamoDB);
    }

    @After
    public void tearDown() {
        awsDynamoDBTestUtils.resetTables(dynamoDB);
    }

    @Test
    public void shouldCreateFlight() {
        FlightInfoResponseDto responseBody = this.webTestClient
                .post()
                .uri(PUSH_ENDPOINT)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(FlightControllerData.testFlight2), FlightPushDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getId()).isEqualTo(FlightControllerData.testFlight2.getId());
        assertThat(responseBody.getIataCode()).isEqualTo(FlightControllerData.testFlight2.getIataCode());
    }

    @Test
    public void shouldGetFlight() {
        FlightInfoResponseDto responseBody = this.webTestClient
                .get()
                .uri(GET_ENDPOINT, FlightControllerData.testFlight.getIataCode(), FlightControllerData.testFlight.getId())
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
                .uri(GET_ENDPOINT, "nooooo", "reerterter")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getId()).isNull();
        assertThat(responseBody.getIataCode()).isNull();
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

    @Test
    public void shouldDeleteFlight() {
        FlightInfoResponseDto responseBody = this.webTestClient
                .post()
                .uri(DELETE_ENDPOINT)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(FlightControllerData.testFlight), FlightPushDto.class)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getId()).isEqualTo(FlightControllerData.testFlight.getId());
        assertThat(responseBody.getIataCode()).isEqualTo(FlightControllerData.testFlight.getIataCode());

        FlightInfoResponseDto emptyResponse = this.webTestClient
                .get()
                .uri(GET_ENDPOINT,
                        FlightControllerData.testFlight.getIataCode(),
                        FlightControllerData.testFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(emptyResponse.getId()).isNull();
        assertThat(emptyResponse.getIataCode()).isNull();
    }

    @Test
    public void shouldReturnAvailableSeats() {
        FlightRemainingSeats flightRemainingSeats = this.webTestClient
                .get()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.testFlight.getIataCode(),
                        FlightControllerData.testFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightRemainingSeats.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeats.getSoldSeats()).isEqualTo(0);
        assertThat(flightRemainingSeats.getTotalSeatsAvailable()).isEqualTo(198);
        assertThat(flightRemainingSeats.isAdmitsNewBookings()).isTrue();

    }

    @Test
    public void shouldReturnNoSeatsFlight() {
        FlightRemainingSeats flightRemainingSeats = this.webTestClient
                .get()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.fullFlight.getIataCode(),
                        FlightControllerData.fullFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightRemainingSeats.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeats.getSoldSeats()).isEqualTo(198);
        assertThat(flightRemainingSeats.getTotalSeatsAvailable()).isEqualTo(198);
        assertThat(flightRemainingSeats.isAdmitsNewBookings()).isFalse();
    }

    @Test
    public void shouldCreateNewReservation() {

        FlightBookingResult bookingResult = this.webTestClient
                .post()
                .uri(NEW_BOOKING_ENDPOINT)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .body(Mono.just(FlightKeysDto.builder()
                        .iataCode(FlightControllerData.testFlight.getIataCode())
                        .id(FlightControllerData.testFlight.getId())
                        .build()), FlightKeysDto.class)
                .exchange()
                .expectBody(FlightBookingResult.class)
                .returnResult()
                .getResponseBody();

        assertThat(bookingResult.getFlightBookingStatus()).isEqualTo(FlightBookingStatus.CONFIRMED);
        assertThat(pnrService.validatePnr(bookingResult.getPnr())).isTrue();

        FlightRemainingSeats flightRemainingSeats = this.webTestClient
                .post()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.testFlight.getIataCode(), FlightControllerData.testFlight.getId())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(FlightRemainingSeats.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeats.getSoldSeats()).isEqualTo(1);
        assertThat(flightRemainingSeats.isAdmitsNewBookings()).isTrue();


    }
}
