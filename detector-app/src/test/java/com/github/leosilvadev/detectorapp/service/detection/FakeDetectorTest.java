package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class FakeDetectorTest {

    @Test
    public void shouldStartDetectorIfNotYetStarted() throws InterruptedException {
        final var detector = new FakeDetector(
                new Lane(0, new Equipment(UUID.randomUUID())),
                detection -> {},
                Executors.newSingleThreadExecutor()
        );

        detector.start();

        Assertions.assertTrue(detector.isRunning());

        detector.stop();

        Assertions.assertFalse(detector.isRunning());

    }

}
