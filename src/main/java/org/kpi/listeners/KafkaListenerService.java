package org.kpi.listeners;

import org.kpi.model.Sensor;
import org.kpi.service.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class KafkaListenerService {

    private final Logger logger = Logger.getLogger(KafkaListenerService.class.getName());

    private final MetricService metricService;

    public KafkaListenerService(MetricService metricService) {
        this.metricService = metricService;
    }

    @KafkaListener(topics = "sensorTopic", groupId = "sensor-consumer-group", containerFactory = "SensorConsumer")
    public void listenSensorData(@Payload Sensor sensor,
                                 @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                                 @Header(KafkaHeaders.OFFSET) String offset,
                                 @Header(KafkaHeaders.RECEIVED_PARTITION_ID) String partition) {
        metricService.saveSensorMetrics(sensor);
        logger.info("| Partiton | Key | Offset | Value ");
        logger.info(String.format("|%s|%s|%s|%s|%n", partition, key, offset, sensor.toString()));
    }

    @KafkaListener(topics = "numberTopic", groupId = "number-consumer-group", containerFactory = "NumberConsumer")
    public void listenNumbers(@Payload Integer number) {
        logger.info("Received number=" + number);
    }
}
