package com.shpl.flightmanager.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PnrServiceTest {

    @Autowired
    private PnrService pnrService;

    @Test
    public void generatePnr() {

        String pnr = pnrService.generatePnr();

        assertThat(pnr.length()).isEqualTo(6);
        assertThat(pnr).isUpperCase();
        System.out.println(pnr);
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

        assertThat(pnrService.validatePnr(pnr0)).isTrue();
        assertThat(pnrService.validatePnr(pnr1)).isTrue();
        assertThat(pnrService.validatePnr(pnr2)).isFalse();
        assertThat(pnrService.validatePnr(pnr3)).isFalse();
        assertThat(pnrService.validatePnr(pnr4)).isFalse();
        assertThat(pnrService.validatePnr(pnr5)).isFalse();
        assertThat(pnrService.validatePnr(pnr6)).isFalse();
        assertThat(pnrService.validatePnr(pnr7)).isFalse();


    }
}
