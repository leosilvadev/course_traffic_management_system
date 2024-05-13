package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
@ConditionalOnProperty(prefix = "equipment", value = "mode", havingValue = "stateful")
public class QueuedDetectionRepository implements DetectionRepository {

    private final JmsTemplate jmsTemplate;

    public QueuedDetectionRepository(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public Detection register(final UUID equipmentId, final Detection detection) {
        System.out.println("Enqueuing a detection...");
        final var registration = new ExternalDetectionRepository.DetectionRegistration(detection.id(), equipmentId, detection.speed());
        jmsTemplate.convertAndSend("detection-registrations", registration);
        return detection;
    }

    @Override
    public List<Detection> registerMany(final UUID equipmentId, final List<Detection> detections) {
        System.out.println("Enqueuing a batch of detections...");
        final var registrations = detections.stream()
                .map(detection -> new ExternalDetectionRepository.DetectionRegistration(detection.id(), equipmentId, detection.speed()))
                .toList();

        final var registration = new ExternalDetectionRepository.DetectionBatchRegistration(registrations);
        jmsTemplate.convertAndSend("detection-registrations-batch", registration);
        return detections;
    }
}
