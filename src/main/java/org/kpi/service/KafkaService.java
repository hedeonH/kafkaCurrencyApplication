package org.kpi.service;

import org.kpi.model.Sensor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.logging.Logger;

@Service
public class KafkaService {

    private final Logger logger = Logger.getLogger(KafkaService.class.getName());
    private final KafkaTemplate<String, Sensor> sensorKafkaTemplate;
    private final KafkaTemplate<String, Integer> numberKafkaTemplate;

    @Value("${kafka.topicName.sensor}")
    private String sensorTopicName;

    @Value("${kafka.topicName.number}")
    private String numberTopicName;

    public KafkaService(@Qualifier("SensorProducer") KafkaTemplate<String, Sensor> sensorKafkaTemplate,
                        @Qualifier("NumberProducer") KafkaTemplate<String, Integer> numberKafkaTemplate) {
        this.sensorKafkaTemplate = sensorKafkaTemplate;
        this.numberKafkaTemplate = numberKafkaTemplate;
    }

    public void sendSensorData(Sensor sensor) {
        ListenableFuture<SendResult<String, Sensor>> result =
                sensorKafkaTemplate.send(sensorTopicName, sensor.getName(), sensor);
        result.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.throwing("Unable to send message=["
                        + sensor + "] due to : ", "sendSensorData", ex);
            }

            @Override
            public void onSuccess(SendResult<String, Sensor> result) {
                logger.info("Sent message=[" + sensor +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }

    public void sendNumericData(Integer value) {
        ListenableFuture<SendResult<String, Integer>> result = numberKafkaTemplate.send(numberTopicName, value);
        result.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.throwing("Unable to send message=["
                        + value + "] due to : ", "sendNumericData", ex);
            }

            @Override
            public void onSuccess(SendResult<String, Integer> result) {
                logger.info("Sent message=[" + value +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
        });
    }

}
