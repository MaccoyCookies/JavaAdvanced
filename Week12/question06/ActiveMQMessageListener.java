package com.maccoy.mq.active.project;

import lombok.extern.slf4j.Slf4j;

import javax.jms.Message;
import javax.jms.MessageListener;

@Slf4j
public class ActiveMQMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("ActiveMQMessageListener.process: {}", message);
    }
}
