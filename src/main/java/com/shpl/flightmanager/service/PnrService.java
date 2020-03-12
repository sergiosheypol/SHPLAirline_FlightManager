package com.shpl.flightmanager.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;


@Service
public class PnrService {

    public String generatePnr() {
        return RandomStringUtils.randomAlphanumeric(6).toUpperCase();
    }

    public Boolean validatePnr(String pnr) {
        return pnr.matches("[A-Z0-9]{6}");
    }

}
