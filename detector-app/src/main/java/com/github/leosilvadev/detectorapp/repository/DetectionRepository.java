package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;

import java.util.List;
import java.util.UUID;

public interface DetectionRepository {

    public Detection register(UUID equipmentId, Detection detection);

    public List<Detection> registerMany(UUID equipmentId, List<Detection> detections);


}
