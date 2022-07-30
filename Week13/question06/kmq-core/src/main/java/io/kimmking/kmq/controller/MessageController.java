package io.kimmking.kmq.controller;


import cn.hutool.core.util.RandomUtil;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;
import io.kimmking.kmq.core.MessageProperties;
import io.kimmking.kmq.demo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {


    @Autowired
    private KmqProducer producer;

    @Autowired
    private KmqConsumer<Order> consumer;

    @Autowired
    private MessageProperties messageProperties;

    @GetMapping("/producer")
    public String send(String topic, HttpServletRequest request) {
        Order order = new Order(RandomUtil.randomLong(), RandomUtil.randomLong(), "maccoy", RandomUtil.randomDouble());
        KmqMessage kmqMessage = KmqMessage.builder().body(order).build();
        boolean sendFlag = producer.send(topic, kmqMessage);
        return sendFlag ? "success" : "failed";
    }

    @GetMapping("/consumer")
    public Object consumer(String topic) {
        return consumer.poll(topic, messageProperties.getConsumerId());
    }

}