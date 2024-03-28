package com.github.leosilvadev.detectorapp.domain;


import java.util.List;
import java.util.UUID;

public record Equipment(UUID id, long lat, long lng, List<Lane> lanes) {
}
