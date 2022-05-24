package org.kpi.service;

import org.kpi.model.Sensor;
import org.kpi.repository.MetricsRepository;
import org.springframework.stereotype.Service;

@Service
public class MetricService {

    private final ValidationService validationService;

    private final MetricsRepository metricsRepository;

    public MetricService(ValidationService validationService, MetricsRepository metricsRepository) {
        this.validationService = validationService;
        this.metricsRepository = metricsRepository;
    }

    public void saveSensorMetrics(Sensor sensor) {
        if (validationService.validateSensorMetrics(sensor)) {
            metricsRepository.save(UtilService.sensorEntity(sensor));
        }
    }
}
