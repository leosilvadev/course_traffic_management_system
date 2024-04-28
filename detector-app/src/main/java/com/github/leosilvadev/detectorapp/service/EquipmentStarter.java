package com.github.leosilvadev.detectorapp.service;

import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import com.github.leosilvadev.detectorapp.service.detection.Detector;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;

import java.util.List;

@Service
public class EquipmentStarter {

    private final Equipment equipment;

    public EquipmentStarter(final Equipment equipment) {
        this.equipment = equipment;
    }

    public List<Disposable> start() {
        return equipment.lanes().stream().map(Lane::detector).map(Detector::start).toList();
    }

}
