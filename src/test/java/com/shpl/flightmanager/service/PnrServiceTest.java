package com.shpl.flightmanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PnrServiceTest {

    @Autowired
    private PnrService pnrService;

    @Test
    public void generatePnr() {
        StepVerifier.create(pnrService.generatePnr())
                .assertNext(pnr -> {
                    assertThat(pnr.length()).isEqualTo(6);
                    assertThat(pnr).isUpperCase();
                    System.out.println(pnr);
                })
                .verifyComplete();
    }

    @Test
    public void validatePnr() {

        String pnr0 = "678954";

        String pnr1 = "AE1R44";

        String pnr2 = "aaRT56";

        String pnr3 = "AAER567";

        String pnr4 = "A 3RTR";

        String pnr5 = "A 3RTREE";

        String pnr6 = "WER";

        String pnr7 = "@RTY78";

        StepVerifier.create(pnrService.validatePnr(pnr0))
                .assertNext(pnr -> assertThat(pnr).isTrue())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr1))
                .assertNext(pnr -> assertThat(pnr).isTrue())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr2))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr3))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr4))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr5))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr6))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();

        StepVerifier.create(pnrService.validatePnr(pnr7))
                .assertNext(pnr -> assertThat(pnr).isFalse())
                .verifyComplete();


    }
}
