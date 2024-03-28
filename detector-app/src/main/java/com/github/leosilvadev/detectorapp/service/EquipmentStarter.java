package com.github.leosilvadev.detectorapp.service;

import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import com.github.leosilvadev.detectorapp.service.detection.Detector;
import org.springframework.stereotype.Service;

@Service
public class EquipmentStarter {

    private final Equipment equipment;

    public EquipmentStarter(final Equipment equipment) {
        this.equipment = equipment;
    }

    public void start() {
        equipment.lanes().stream().map(Lane::detector).forEach(Detector::start);
    }

}
