package com.github.leosilvadev.detectorapp.service.processor;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.repository.DetectionRepository;

import java.util.List;
import java.util.UUID;

public class DetectionProcessor implements Processor<Detection> {

    private final UUID equipmentId;

    private final DetectionRepository repository;

    public DetectionProcessor(final UUID equipmentId, final DetectionRepository repository) {
        this.equipmentId = equipmentId;
        this.repository = repository;
    }

    @Override
    public List<Detection> onEvents(final List<Detection> detections) {
        repository.registerMany(this.equipmentId, detections);
        return detections;
    }

    @Override
    public void onError(final Throwable ex) {
        ex.printStackTrace();
    }
}
