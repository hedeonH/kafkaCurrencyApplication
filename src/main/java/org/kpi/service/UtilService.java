package org.kpi.service;

import org.kpi.model.Sensor;
import org.kpi.model.SensorEntity;

public class UtilService {

    private UtilService(){}

    public static SensorEntity sensorEntity(Sensor sensor){
        SensorEntity sensorEntity = new SensorEntity();
        sensorEntity.setHumidity(sensor.getHumidity());
        sensorEntity.setName(sensor.getName());
        sensorEntity.setTemperature(sensor.getTemperature());
        return sensorEntity;
    }
}
