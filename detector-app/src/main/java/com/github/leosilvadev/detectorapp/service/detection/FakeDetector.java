package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Plate;
import com.github.leosilvadev.detectorapp.service.processor.Processor;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class FakeDetector implements Detector {

    private final Processor<Detection> processor;

    private final ExecutorService executorService;

    private final AtomicReference<Future<?>> activeDetectionExecution;

    private final Random random;

    public FakeDetector(
            final Processor<Detection> processor,
            final ExecutorService executorService
    ) {
        this.processor = processor;
        this.executorService = executorService;
        this.activeDetectionExecution = new AtomicReference<>();
        this.random = new Random();
    }

    @Override
    public void start() {
        if (this.isRunning()) {
            return;
        }

        final var execution = executorService.submit(() -> {
            final var currentThread = Thread.currentThread();
            while (currentThread.isAlive()) {
                try {
                    Thread.sleep(500);

                    processor.onEvent(
                            new Detection(
                                    UUID.randomUUID(),
                                    Plate.generate(),
                                    random.nextDouble(50.0, 100.0),
                                    Instant.now()
                            )
                    );

                } catch (final Throwable ex) {
                    processor.onError(ex);

                }
            }
        });

        this.activeDetectionExecution.set(execution);
    }

    @Override
    public void stop() {
        final var processRunning = this.activeDetectionExecution.get();
        if (processRunning != null) {
            processRunning.cancel(true);
        }
    }

    @Override
    public boolean isRunning() {
        final var processRunning = this.activeDetectionExecution.get();
        return processRunning != null && (!processRunning.isCancelled() && !processRunning.isDone());
    }
}
