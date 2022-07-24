package com.maccoy.mq.active.project;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class MqActiveProjectApplication {

    public static void main(String[] args) throws JMSException, InterruptedException {
        Destination activeMQTopic = new ActiveMQTopic("test.topic");
        Destination activeMQQueue = new ActiveMQQueue("test.queue");

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // // 设置消费者
        MessageConsumer topicConsumer = session.createConsumer(activeMQTopic);
        topicConsumer.setMessageListener(new ActiveMQMessageListener());
        MessageConsumer queueConsumer = session.createConsumer(activeMQTopic);
        queueConsumer.setMessageListener(new ActiveMQMessageListener());

        // 创建消费者
        MessageProducer topicProducer = session.createProducer(activeMQTopic);
        ActiveMQMessageSender topicProducerSender = new ActiveMQMessageSender(topicProducer);
        int i = 0;
        while (i++ < 10) {
            topicProducerSender.send(session.createTextMessage("num: " + i + ", test topic"));
        }
        MessageProducer queueProducer = session.createProducer(activeMQQueue);
        ActiveMQMessageSender queueProducerSender = new ActiveMQMessageSender(queueProducer);
        i = 0;
        while (i++ < 10) {
            queueProducerSender.send(session.createTextMessage("num: " + i + ", test queue"));
        }
        Thread.sleep(10000);

        topicProducer.close();
        queueProducer.close();
        topicConsumer.close();
        queueConsumer.close();
        connection.close();
        session.close();

    }

}
