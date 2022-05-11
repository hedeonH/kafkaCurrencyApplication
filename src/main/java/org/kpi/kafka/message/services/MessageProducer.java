package org.kpi.kafka.message.services;

import org.kpi.kafka.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.lang.System.out;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, CurrencyRate> greetingKafkaTemplate;

    @Value(value = "${message.topic.name}")
    private String topicName;

    @Value(value = "${partitioned.topic.name}")
    private String partitionedTopicName;

    @Value(value = "${filtered.topic.name}")
    private String filteredTopicName;

    @Value(value = "${currencyRate.topic.name}")
    private String currencyTopicName;

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

    public void sendMessageToPartition(String message, int partition) {
        kafkaTemplate.send(partitionedTopicName, partition, null, message);
    }

    public void sendMessageToFiltered(String message) {
        kafkaTemplate.send(filteredTopicName, message);
    }

    public void sendGreetingMessage(CurrencyRate currencyRate) {
        greetingKafkaTemplate.send(currencyTopicName, currencyRate);
    }
}
