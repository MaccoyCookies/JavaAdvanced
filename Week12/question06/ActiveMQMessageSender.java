package com.maccoy.mq.active.project;

import lombok.extern.slf4j.Slf4j;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

@Slf4j
public class ActiveMQMessageSender {

    private final MessageProducer messageProducer;

    public ActiveMQMessageSender(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void send(Message message) throws JMSException {
        messageProducer.send(message);
        log.info("message send success, message: {}", message);
    }

}
