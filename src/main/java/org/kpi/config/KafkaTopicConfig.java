package org.kpi.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value("${kafka.topicName.sensor}")
    private String sensorTopicName;

    @Value("${kafka.topicName.number}")
    private String numberTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic sensorTopic() {
        return new NewTopic(sensorTopicName, 3, (short) 1);
    }

    @Bean
    public NewTopic numberTopic() {
        return new NewTopic(numberTopicName, 1, (short) 1);
    }
}