package com.github.leosilvadev.detectorapp.domain;

import com.github.leosilvadev.detectorapp.service.detection.Detector;

public record Lane(int id, Detector detector) {
}
