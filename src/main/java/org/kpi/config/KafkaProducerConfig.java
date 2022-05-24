package org.kpi.config;

import org.kpi.model.Sensor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Sensor> sensorProducerFactory() {
        Map<String, Object> configProps = getConfigProperties();
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, Integer> numberProducerFactory() {
        Map<String, Object> configProps = getConfigProperties();
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                IntegerSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean("SensorProducer")
    public KafkaTemplate<String, Sensor> sensorKafkaTemplate() {
        return new KafkaTemplate<>(sensorProducerFactory());
    }

    @Bean("NumberProducer")
    public KafkaTemplate<String, Integer> numberKafkaTemplate() {
        return new KafkaTemplate<>(numberProducerFactory());
    }

    private Map<String, Object> getConfigProperties() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return configProps;
    }
}