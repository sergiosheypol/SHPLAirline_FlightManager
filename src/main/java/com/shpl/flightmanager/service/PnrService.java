package com.shpl.flightmanager.service;

import org.springframework.stereotype.Service;

@Service
public class PnrService {

    public String generatePnr() {
        return "sfgfdfrgre";
    }

    public Boolean validatePnr(String pnr) {
        return true;
    }
}
