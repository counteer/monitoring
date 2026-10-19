package com.aldisued.iot.monitoring.service;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.entity.Sensor;
import com.aldisued.iot.monitoring.exception.AlertNotFoundException;
import com.aldisued.iot.monitoring.exception.SensorAlreadyExistsException;
import com.aldisued.iot.monitoring.exception.SensorNotFoundException;
import com.aldisued.iot.monitoring.repository.AlertRepository;
import com.aldisued.iot.monitoring.repository.SensorRepository;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final SensorRepository sensorRepository;
    private final KafkaTemplate<String, AlertDto> kafkaTemplate;

    public AlertService(AlertRepository alertRepository, SensorRepository sensorRepository,
                        KafkaTemplate<String, AlertDto> kafkaTemplate) {
        this.alertRepository = alertRepository;
        this.sensorRepository = sensorRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Alert saveAlert(AlertDto alertDto) {
        UUID sensorId = alertDto.sensorId();
        Sensor sensor = sensorRepository
                .findById(sensorId)
                .orElseThrow(() -> new SensorNotFoundException(sensorId));
        Alert alert =  alertRepository.save(new Alert( alertDto.message(), alertDto.timestamp(), sensor));
        kafkaTemplate.send("alerts", alertDto);
        return alert;
    }

    public AlertDto findLastAlertBySensorId(UUID sensorId) {
        Alert alert = alertRepository
                .findFirstBySensorIdOrderByTimestampDesc(sensorId)
                    .orElseThrow(() -> new AlertNotFoundException(sensorId));
        return new AlertDto(sensorId, alert.getMessage(), alert.getTimestamp());
    }
}
