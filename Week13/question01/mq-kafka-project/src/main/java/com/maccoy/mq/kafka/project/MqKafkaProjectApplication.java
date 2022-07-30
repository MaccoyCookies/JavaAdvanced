package com.maccoy.mq.kafka.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MqKafkaProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqKafkaProjectApplication.class, args);
    }

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @GetMapping("/test")
    public void test() {
        kafkaTemplate.send("com.maccoy.testKafka", "test!");
    }
}
