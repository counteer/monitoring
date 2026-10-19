package com.aldisued.iot.monitoring.controller;

import com.aldisued.iot.monitoring.dto.AlertDto;
import com.aldisued.iot.monitoring.entity.Alert;
import com.aldisued.iot.monitoring.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping(path = "latest")
    public AlertDto getLatest(@RequestParam(name = "sensorId") UUID sensorId) {
        return alertService.findLastAlertBySensorId(sensorId);
    }

    @PostMapping
    public AlertDto save(@RequestBody AlertDto alertDto) {
        Alert alert =  alertService.saveAlert(alertDto);
        return new AlertDto(alert.getSensor().getId(), alert.getMessage(), alert.getTimestamp());
    }


}
