package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class FakeDetectorTest {

    @Test
    public void shouldStartDetectorIfNotYetStarted() throws InterruptedException {
        final var lanes = List.of(new Lane(0));
        final var equipment = new Equipment(UUID.randomUUID(), 1, 2, lanes);
        final var detector = new FakeDetector(
                equipment,
                lanes.get(0),
                detection -> {},
                Executors.newSingleThreadExecutor()
        );

        detector.start();

        Assertions.assertTrue(detector.isRunning());

        detector.stop();

        Assertions.assertFalse(detector.isRunning());

    }

}
