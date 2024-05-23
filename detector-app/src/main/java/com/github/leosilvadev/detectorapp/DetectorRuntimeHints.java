package com.github.leosilvadev.detectorapp;

import com.github.leosilvadev.detectorapp.domain.DetectionBatchRegistration;
import com.github.leosilvadev.detectorapp.repository.ExternalDetectionRepository;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class DetectorRuntimeHints implements RuntimeHintsRegistrar {
    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.serialization().registerType(DetectionBatchRegistration.DetectionRegistration.class);
    }
}
