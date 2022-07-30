package io.kimmking.kmq.core;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Broker+Connection
 */
@Configuration
public class KmqBroker implements InitializingBean {

    @Autowired
    private MessageProperties messageProperties;

    private final Map<String, ArrayQueue> kmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        kmqMap.putIfAbsent(name, new ArrayQueue(messageProperties.getLength()));
    }

    public ArrayQueue findKmq(String topic) {
        return this.kmqMap.get(topic);
    }

    @Bean
    public KmqProducer createProducer() {
        return new KmqProducer(this);
    }

    @Bean
    public KmqConsumer createConsumer() {
        return new KmqConsumer(this);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (String topic : messageProperties.getTopicName()) {
            createTopic(topic);
        }
    }
}