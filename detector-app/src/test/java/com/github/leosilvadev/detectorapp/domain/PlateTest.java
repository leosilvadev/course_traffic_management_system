package com.github.leosilvadev.detectorapp.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlateTest {

    private final Pattern pattern = Pattern.compile("[A-Z]{3}[0-9]{4}");

    @Test
    public void shouldGenerateAValidPlate() {
        final var plate = Plate.generate();

        assertTrue(pattern.matcher(plate.code()).find());
    }

}
