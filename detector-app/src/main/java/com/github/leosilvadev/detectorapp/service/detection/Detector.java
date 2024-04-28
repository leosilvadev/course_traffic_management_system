package com.github.leosilvadev.detectorapp.service.detection;

import reactor.core.Disposable;

public interface Detector {

    Disposable start();

    void stop();

    boolean isRunning();

}
