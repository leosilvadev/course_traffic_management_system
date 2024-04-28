package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class FakeDetector implements Detector {

    private final Flux<Detection> detectionGenerator;

    private final Processor<Detection> processor;

    private final ExecutorService executorService;

    private final AtomicReference<Disposable> activeDetectionExecution;

    private final Random random;

    public FakeDetector(
            final Flux<Detection> detectionGenerator,
            final Processor<Detection> processor,
            final ExecutorService executorService
    ) {
        this.detectionGenerator = detectionGenerator;
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

        final var disposable = detectionGenerator.map(processor::onEvent)
                .onErrorContinue((ex, __) -> processor.onError(ex))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .subscribe();

        this.activeDetectionExecution.set(disposable);
    }

    @Override
    public void stop() {
        final var processRunning = this.activeDetectionExecution.get();
        if (processRunning != null) {
            processRunning.dispose();
        }
    }

    @Override
    public boolean isRunning() {
        final var processRunning = this.activeDetectionExecution.get();
        return processRunning != null && !processRunning.isDisposed();
    }
}
