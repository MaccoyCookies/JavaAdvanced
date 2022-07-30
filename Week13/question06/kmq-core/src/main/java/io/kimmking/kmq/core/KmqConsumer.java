package io.kimmking.kmq.core;


public class KmqConsumer<T> {

    private final KmqBroker broker;

    private ArrayQueue kmq;

    public KmqConsumer(KmqBroker broker) {
        this.broker = broker;
    }

    public KmqMessage<T> poll(String topic, String consumerId) {
        this.kmq = this.broker.findKmq(topic);
        if (null == kmq) throw new RuntimeException("Topic[" + topic + "] doesn't exist.");
        return kmq.poll(consumerId);
    }

}