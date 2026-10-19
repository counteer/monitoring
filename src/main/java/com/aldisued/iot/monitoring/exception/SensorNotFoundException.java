package com.aldisued.iot.monitoring;

import java.util.UUID;

public class SensorNotFoundException extends RuntimeException {

    public SensorNotFoundException(UUID sensorId) {
        super("Sensor not found with id: " + sensorId);
    }
}