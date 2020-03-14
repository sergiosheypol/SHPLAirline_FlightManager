package com.shpl.flightmanager.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class PnrService {

    public Mono<String> generatePnr() {
        return Mono.justOrEmpty(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
    }

    public Mono<Boolean> validatePnr(String pnr) {
        return Mono.justOrEmpty(pnr.matches("[A-Z0-9]{6}"));
    }

}
