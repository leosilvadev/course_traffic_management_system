package com.github.leosilvadev.detectorapp.domain;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public record Plate(String code) {

    public static Plate generate() {
        return new Plate(randomAlphabetic(3) + randomNumeric(4));
    }

}
