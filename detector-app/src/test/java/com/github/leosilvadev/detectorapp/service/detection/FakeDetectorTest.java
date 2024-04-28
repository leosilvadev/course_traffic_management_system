package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Plate;
import com.github.leosilvadev.detectorapp.service.processor.DetectionProcessor;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FakeDetectorTest {

    @Test
    public void shouldStartDetectorIfNotYetStarted() {
        final var detector = new FakeDetector(
                Flux.empty(),
                new Processor<>() {
                    @Override
                    public Detection onEvent(Detection event) {
                        return null;
                    }

                    @Override
                    public void onError(Throwable ex) {

                    }
                },
                Executors.newSingleThreadExecutor()
        );

        detector.start();

        assertTrue(detector.isRunning());

        detector.stop();

        assertFalse(detector.isRunning());
    }

    @Test
    public void shouldThrowAnErrorDuringProcessingButKeepRunning() throws InterruptedException {
        final var mock = mock(DetectionProcessor.class);
        final var detection = new Detection(UUID.randomUUID(), Plate.generate(), 100, Instant.now());

        when(mock.onEvent(any(Detection.class)))
                .thenThrow(RuntimeException.class)
                .thenReturn(detection);

        final var detector = new FakeDetector(
                Flux.just(detection, detection, detection),
                mock,
                Executors.newSingleThreadExecutor()
        );

        detector.start();

        assertTrue(detector.isRunning());

        Thread.sleep(2_000);

        detector.stop();

        assertFalse(detector.isRunning());

        verify(mock, times(1)).onError(any(RuntimeException.class));
        verify(mock, atLeast(3)).onEvent(any(Detection.class));
    }

}
