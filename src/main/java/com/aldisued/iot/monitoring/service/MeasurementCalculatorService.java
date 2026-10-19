package com.aldisued.iot.monitoring.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MeasurementCalculatorService {

    public List<Double> filterByAverageDeviation(List<Double> values, Double deviation) {
        validateDeviation(deviation);

        if (values.isEmpty()) {
            return List.of();
        }

        double average = values.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow();
        double maxDeviation = Math.abs(average) * deviation;

        return values.stream()
                .filter(value -> Math.abs(value - average) <= maxDeviation)
                .toList();
    }

    public List<Double> getMovingAverage(List<Double> data, int windowSize) {
        if (windowSize <= 0 || windowSize > data.size()) {
            throw new IllegalArgumentException("Invalid window size");
        }
        List<Double> movingAverage = new ArrayList<>();
        double sum = 0.0;
        for (int i = 0; i < data.size(); i++) {
            sum += data.get(i);
            if (i >= windowSize) {
                sum -= data.get(i - windowSize);
            }
            if (i >= windowSize - 1) {
                movingAverage.add(sum / windowSize);
            }
        }
        return movingAverage;
    }

    private void validateDeviation(Double deviation) {
        if (deviation == null || Double.isNaN(deviation)
                || deviation < 0.0 || deviation > 1.0) {
            throw new IllegalArgumentException(
                    "Deviation must be between 0.0 and 1.0"
            );
        }
    }

}
