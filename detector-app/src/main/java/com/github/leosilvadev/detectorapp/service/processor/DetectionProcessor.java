package com.github.leosilvadev.detectorapp.service.processor;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.repository.DetectionRepository;

import java.util.UUID;

public class DetectionProcessor implements Processor<Detection> {

    private final UUID equipmentId;

    private final DetectionRepository repository;

    public DetectionProcessor(final UUID equipmentId, final DetectionRepository repository) {
        this.equipmentId = equipmentId;
        this.repository = repository;
    }

    @Override
    public Detection onEvent(final Detection detection) {
        System.out.println("Trying to register a detection...");
        repository.register(this.equipmentId, detection);
        return detection;
    }

    @Override
    public void onError(final Throwable ex) {
        ex.printStackTrace();
    }
}
