package com.github.leosilvadev.detectorapp.service.detection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

public class FakeDetectorTest {

    @Test
    public void shouldStartDetectorIfNotYetStarted() {
        final var detector = new FakeDetector(
                detection -> {},
                Executors.newSingleThreadExecutor()
        );

        detector.start();

        Assertions.assertTrue(detector.isRunning());

        detector.stop();

        Assertions.assertFalse(detector.isRunning());

    }

}
