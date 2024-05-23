package com.github.leosilvadev.detectorapp.service.detection;

import com.github.leosilvadev.detectorapp.domain.DetectionBatchRegistration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(prefix = "equipment", value = "mode", havingValue = "stateful")
public class EnqueuedDetectionsProcessorJob {

    @JmsListener(destination = "detection-registrations-batch")
    public void process(final DetectionBatchRegistration batchRegistration) {
        System.out.println(batchRegistration);
    }

}
