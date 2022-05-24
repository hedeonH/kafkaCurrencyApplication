package org.kpi.tasks;

import org.kpi.service.KafkaService;
import org.kpi.model.Sensor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;


@EnableScheduling
@Component
public class SimulationTask {
    private final Random random = new Random();
    private final KafkaService kafkaService;

    public SimulationTask(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @Scheduled(fixedRate = 10)
    public void simulateSensorData() {
        Sensor sensor = new Sensor();
        sensor.setName("mac-" + random.nextInt(100));
        sensor.setHumidity(random.nextInt(101));
        sensor.setTemperature(-30f + random.nextFloat() * (60));
        kafkaService.sendSensorData(sensor);
    }

    @Scheduled(fixedRate = 1000)
    public void simulateNumbersData() {
        kafkaService.sendNumericData(random.nextInt(1_00));
    }
}
