package org.kpi.service;

import org.kpi.model.Sensor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.logging.Logger;

@Service
public class ValidationService {

    public static final String VALIDATION_MESSAGE = "{0} is not valid because the {1}";
    private final Logger logger = Logger.getLogger(ValidationService.class.getName());

    public boolean validateSensorMetrics(Sensor sensor) {
        if (sensor.getHumidity() > 100 || sensor.getHumidity() < 0) {
            logger.warning(MessageFormat.format(VALIDATION_MESSAGE, sensor, "humidity"));
            return false;
        }
        if (sensor.getTemperature() > 40 || sensor.getTemperature() < -40) {
            logger.warning(MessageFormat.format(VALIDATION_MESSAGE, sensor, "temperature"));
            return false;
        }
        return true;
    }
}

