package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.DetectionBatchRegistration;
import com.github.leosilvadev.detectorapp.repository.ExternalDetectionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "equipment", value = "mode", havingValue = "stateful")
public class EnqueuedDetectionsProcessorJob {

    private final ExternalDetectionRepository externalDetectionRepository;

    public EnqueuedDetectionsProcessorJob(final ExternalDetectionRepository externalDetectionRepository) {
        this.externalDetectionRepository = externalDetectionRepository;
    }

    @JmsListener(destination = "detection-registrations-batch")
    public void process(final DetectionBatchRegistration batchRegistration) {
        externalDetectionRepository.doRegisterMany(batchRegistration);
    }

}
