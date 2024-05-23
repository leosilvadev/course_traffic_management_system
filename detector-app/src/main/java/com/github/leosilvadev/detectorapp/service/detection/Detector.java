package com.github.leosilvadev.detectorapp.service.detection;

import reactor.core.Disposable;

import java.time.Duration;

public interface Detector {

    Disposable start();

    void stop();

    boolean isRunning();

    public record DetectionBufferSpec(Integer maxSize, Duration duration) {

    }

}
