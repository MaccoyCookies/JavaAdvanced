package com.maccoy.mq.kafka.project.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DemoListener {

    /**
     * 声明consumerID为demo，监听topicName为topic.quick.demo的Topic
     */
    @KafkaListener(id = "demo", topics = "com.maccoy.testKafka")
    public void listen(String msgData) {
        log.info("demo receive : " + msgData);
    }
}
