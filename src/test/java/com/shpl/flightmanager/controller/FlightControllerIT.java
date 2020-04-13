package com.shpl.flightmanager.controller;

import com.shpl.flightmanager.FlightbookingApplication;
import com.shpl.flightmanager.config.MongoDBUtils;
import com.shpl.flightmanager.dto.FlightBookingResultDto;
import com.shpl.flightmanager.dto.FlightBookingStatus;
import com.shpl.flightmanager.dto.FlightExistsDto;
import com.shpl.flightmanager.dto.FlightInfoResponseDto;
import com.shpl.flightmanager.dto.FlightPushDto;
import com.shpl.flightmanager.dto.FlightRemainingSeatsDto;
import com.shpl.flightmanager.service.PnrService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest(classes = {FlightController.class, FlightbookingApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class FlightControllerIT {

    private final static String PUSH_ENDPOINT = "/flight/pushFlight";
    private final static String GET_ENDPOINT = "/flight/getFlight/{flightId}";
    private final static String DELETE_ENDPOINT = "/flight/deleteFlight/{flightId}";
    private final static String AVAILABLE_SEATS_ENDPOINT = "/flight/availableSeats/{flightId}";
    private final static String NEW_BOOKING_ENDPOINT = "/flight/saveNewBooking/{flightId}";
    private final static String AVAILABLE_FLIGHT_ENDPOINT = "/flight/isFlightAvailable/{flightId}";

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    MongoDBUtils mongoDBUtils;
    @Autowired
    private PnrService pnrService;

    @Before
    public void setUp() {
        mongoDBUtils.deleteColletion();
        mongoDBUtils.createCollection();
        mongoDBUtils.createTestFlight();
        mongoDBUtils.createFullFlight();
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
                .uri(GET_ENDPOINT, FlightControllerData.testFlight.getId())
                .exchange()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(responseBody.getId()).isEqualTo(FlightControllerData.testFlight.getId());
    }

    @Test
    public void shouldDeleteFlight() {

        FlightInfoResponseDto deleteResponse = this.webTestClient
                .delete()
                .uri(DELETE_ENDPOINT, FlightControllerData.testFlight.getId())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightInfoResponseDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(deleteResponse.getId()).isEqualTo(FlightControllerData.testFlight.getId());
        assertThat(deleteResponse.getIataCode()).isEqualTo(FlightControllerData.testFlight.getIataCode());

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
    public void shouldGetNullObjectWhenUnexistingFlight() {
        FlightInfoResponseDto responseBody = this.webTestClient
                .get()
                .uri(GET_ENDPOINT, "nooooo")
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
    public void shouldReturnAvailableSeats() {

        FlightRemainingSeatsDto flightRemainingSeatsDto = this.webTestClient
                .get()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.testFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightRemainingSeatsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeatsDto.getSoldSeats()).isEqualTo(0);
        assertThat(flightRemainingSeatsDto.getTotalSeatsAvailable()).isEqualTo(198);
        assertThat(flightRemainingSeatsDto.isAdmitsNewBookings()).isTrue();

    }

    @Test
    public void shouldReturnNoSeatsFlight() {
        FlightRemainingSeatsDto flightRemainingSeatsDto = this.webTestClient
                .get()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.fullFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightRemainingSeatsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeatsDto.getSoldSeats()).isEqualTo(198);
        assertThat(flightRemainingSeatsDto.getTotalSeatsAvailable()).isEqualTo(198);
        assertThat(flightRemainingSeatsDto.isAdmitsNewBookings()).isFalse();
    }

    @Test
    public void shouldCreateNewReservation() {

        FlightBookingResultDto bookingResult = this.webTestClient
                .post()
                .uri(NEW_BOOKING_ENDPOINT, FlightControllerData.testFlight.getId())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(FlightBookingResultDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(bookingResult.getFlightBookingStatus()).isEqualTo(FlightBookingStatus.CONFIRMED);

        FlightRemainingSeatsDto flightRemainingSeatsDto = this.webTestClient
                .get()
                .uri(AVAILABLE_SEATS_ENDPOINT, FlightControllerData.testFlight.getId())
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .exchange()
                .expectBody(FlightRemainingSeatsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightRemainingSeatsDto.getSoldSeats()).isEqualTo(1);
        assertThat(flightRemainingSeatsDto.isAdmitsNewBookings()).isTrue();


    }

    @Test
    public void shouldReturnAvailableFlight() {
        FlightExistsDto flightExistsDto = this.webTestClient
                .get()
                .uri(AVAILABLE_FLIGHT_ENDPOINT, FlightControllerData.fullFlight.getId())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightExistsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightExistsDto.isFlightAvailable()).isTrue();
    }

    @Test
    public void shouldReturnNotAvailableFlight() {
        FlightExistsDto flightExistsDto = this.webTestClient
                .get()
                .uri(AVAILABLE_FLIGHT_ENDPOINT, "ererewterter")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(FlightExistsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(flightExistsDto.isFlightAvailable()).isFalse();
    }

}
