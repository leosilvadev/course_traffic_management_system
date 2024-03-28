package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Plate;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class FakeDetector implements Detector {

    private final Consumer<Detection> processor;

    private final ExecutorService executorService;

    private final AtomicReference<Future<?>> detectionRunning;

    private final Random random;

    public FakeDetector(
            final Consumer<Detection> processor,
            final ExecutorService executorService
    ) {
        this.processor = processor;
        this.executorService = executorService;
        this.detectionRunning = new AtomicReference<>();
        this.random = new Random();
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            return;
        }

        final var execution = executorService.submit(() -> {
            while (this.detectionRunning.get() != null && !this.detectionRunning.get().isCancelled()) {
                processor.accept(
                        new Detection(
                                UUID.randomUUID(),
                                Plate.generate(),
                                random.nextDouble(50.0, 100.0),
                                Instant.now()
                        )
                );
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.detectionRunning.set(execution);
    }

    @Override
    public void stop() {
        final var processRunning = this.detectionRunning.get();
        if (processRunning != null) {
            processRunning.cancel(true);
        }
    }

    @Override
    public boolean isRunning() {
        final var processRunning = this.detectionRunning.get();
        return processRunning != null && !processRunning.isCancelled();
    }
}
