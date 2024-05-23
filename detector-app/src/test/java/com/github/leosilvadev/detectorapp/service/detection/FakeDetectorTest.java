package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Plate;
import com.github.leosilvadev.detectorapp.service.processor.DetectionProcessor;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

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
                    public List<Detection> onEvents(List<Detection> events) {
                        return null;
                    }

                    @Override
                    public void onError(Throwable ex) {

                    }
                },
                Executors.newSingleThreadExecutor(),
                new Detector.DetectionBufferSpec(1, Duration.ofMinutes(1))
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

        when(mock.onEvents(any()))
                .thenThrow(RuntimeException.class)
                .thenReturn(List.of(detection));

        final var detector = new FakeDetector(
                Flux.just(detection, detection, detection),
                mock,
                Executors.newSingleThreadExecutor(),
                new Detector.DetectionBufferSpec(1, Duration.ofMinutes(1))
        );

        detector.start();

        assertTrue(detector.isRunning());

        Thread.sleep(2_000);

        detector.stop();

        assertFalse(detector.isRunning());

        verify(mock, times(1)).onError(any(RuntimeException.class));
        verify(mock, atLeast(3)).onEvents(any());
    }

}
