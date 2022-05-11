package org.kpi.kafka.message.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kpi.kafka.model.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TestService {

    @Autowired
    public final MessageProducer messageProducer;

    @Autowired
    public final MessageListener messageListener;

    public TestService(MessageProducer messageProducer, MessageListener messageListener) {
        this.messageProducer = messageProducer;
        this.messageListener = messageListener;
    }

    public void test() throws InterruptedException {

        /*
         * Sending a Hello World message to topic 'baeldung'.
         * Must be received by both listeners with group foo
         * and bar with containerFactory fooKafkaListenerContainerFactory
         * and barKafkaListenerContainerFactory respectively.
         * It will also be received by the listener with
         * headersKafkaListenerContainerFactory as container factory.
         */
        CurrencyRate dollar = new CurrencyRate("Dollar","USD" ,11.12);
        CurrencyRate euro = new CurrencyRate("Euro","EURO" ,17.10);
        CurrencyRate zloty = new CurrencyRate("Zloty","ZL" ,6.4);
        messageProducer.sendMessage("Hello, World!");
        messageListener.latch.await(10, TimeUnit.SECONDS);

        /*
         * Sending message to a topic with 5 partitions,
         * each message to a different partition. But as per
         * listener configuration, only the messages from
         * partition 0 and 3 will be consumed.
         */
        for (int i = 0; i < 5; i++) {
            messageProducer.sendMessageToPartition(dollar.toString(), i);
        }
        messageListener.partitionLatch.await(10, TimeUnit.SECONDS);

        /*
         * Sending message to 'filtered' topic. As per listener
         * configuration,  all messages with char sequence
         * 'World' will be discarded.
         */
        messageProducer.sendMessageToFiltered(euro.toString());
        messageProducer.sendMessageToFiltered(zloty.toString());
        messageListener.filterLatch.await(10, TimeUnit.SECONDS);

        /*
         * Sending message to 'greeting' topic. This will send
         * and received a java object with the help of
         * greetingKafkaListenerContainerFactory.
         */
        ObjectMapper mapper = new ObjectMapper();
        messageProducer.sendGreetingMessage(zloty);
        messageListener.greetingLatch.await(10, TimeUnit.SECONDS);

    }
}
