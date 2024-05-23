package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;

public class FakeDetector implements Detector {

    private final Flux<Detection> detectionGenerator;

    private final Processor<Detection> processor;

    private final ExecutorService executorService;

    private final DetectionBufferSpec bufferSpec;

    private final AtomicReference<Disposable> activeDetectionExecution;

    public FakeDetector(
            final Flux<Detection> detectionGenerator,
            final Processor<Detection> processor,
            final ExecutorService executorService,
            final DetectionBufferSpec bufferSpec
    ) {
        this.detectionGenerator = detectionGenerator;
        this.processor = processor;
        this.executorService = executorService;
        this.bufferSpec = bufferSpec;
        this.activeDetectionExecution = new AtomicReference<>();
    }

    @Override
    public Disposable start() {
        if (this.isRunning()) {
            return this.activeDetectionExecution.get();
        }

        final var disposable = detectionGenerator
                .bufferTimeout(bufferSpec.maxSize(), bufferSpec.duration())
                .map(processor::onEvents)
                .onErrorContinue((ex, __) -> processor.onError(ex))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .subscribe();

        this.activeDetectionExecution.set(disposable);
        return disposable;
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
