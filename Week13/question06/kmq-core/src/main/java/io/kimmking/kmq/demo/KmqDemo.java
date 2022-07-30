package io.kimmking.kmq.demo;

import cn.hutool.core.util.IdUtil;
import io.kimmking.kmq.core.KmqBroker;
import io.kimmking.kmq.core.KmqConsumer;
import io.kimmking.kmq.core.KmqMessage;
import io.kimmking.kmq.core.KmqProducer;

import lombok.SneakyThrows;

public class KmqDemo {

    @SneakyThrows
    public static void main(String[] args) {
        String topic = "com.maccoy";
        KmqBroker broker = new KmqBroker();
        broker.createTopic(topic);

        KmqConsumer<Order> consumer = broker.createConsumer();
        final boolean[] flag = new boolean[1];
        flag[0] = true;
        new Thread(() -> {
            String consumerId = IdUtil.fastSimpleUUID();
            while (flag[0]) {
                KmqMessage<Order> message = consumer.poll(topic, consumerId);
                if (null != message) {
                    System.out.println(message.getBody());
                }
            }
            System.out.println("程序退出。");
        }).start();

        KmqProducer producer = broker.createProducer();
        for (int i = 0; i < 10; i++) {
            Order order = new Order(1000L + i, System.currentTimeMillis(), "USD2CNY", 6.51d);
            boolean sendFlag = producer.send(topic, new KmqMessage<>(null, order));
            if (!sendFlag) {
                System.out.println("发送失败!!!");
            }
        }
        Thread.sleep(500);
        System.out.println("点击任何键，发送一条消息；点击q或e，退出程序。");
        while (true) {
            char c = (char) System.in.read();
            if (c == 'q' || c == 'e') break;
            if (c > 20) {
                Order order = new Order(100000L + c, System.currentTimeMillis(), "USD2CNY", 6.52d);
                boolean sendFlag = producer.send(topic, new KmqMessage<>(null, order));
                if (!sendFlag) {
                    System.out.println("发送失败!!!");
                }
            }
        }
        flag[0] = false;
    }
}