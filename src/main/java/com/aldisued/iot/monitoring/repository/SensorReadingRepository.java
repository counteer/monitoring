package com.aldisued.iot.monitoring.repository;

import com.aldisued.iot.monitoring.entity.SensorReading;
import com.aldisued.iot.monitoring.entity.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface SensorReadingRepository extends JpaRepository<SensorReading, String> {

    @Query("""
            SELECT AVG(sr.value)
            FROM SensorReading sr
            WHERE sr.sensor.type = :type
              AND sr.timestamp BETWEEN :start AND :end
            """)
    Optional<Double> findAverageByTypeAndPeriod(
            SensorType type,
            LocalDateTime start,
            LocalDateTime end);

    @Query("""
            SELECT sr.value
            FROM SensorReading sr
            WHERE sr.sensor.type = :type
              AND sr.timestamp BETWEEN :from AND :to
            ORDER BY sr.timestamp ASC
            """)
    List<Double> findValuesBySensorTypeAndTimestampBetween(
            SensorType type,
            LocalDateTime from,
            LocalDateTime to
    );
}
