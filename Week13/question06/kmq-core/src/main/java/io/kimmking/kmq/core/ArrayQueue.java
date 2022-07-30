package io.kimmking.kmq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ArrayQueue<T> {

    /**
     * 数据存放数组
     */
    private final KmqMessage<T>[] arr;

    /**
     * 写入位置
     */
    private int pushIndex;

    /**
     * 读取位置
     */
    private final Map<String, Integer> consumerOffsetMap;

    /**
     * 总数据大小
     */
    private int size;

    /**
     * 容器大小
     */
    private final int limit;

    public ArrayQueue(int limit) {
        this.limit = limit;
        this.arr = new KmqMessage[limit];
        this.pushIndex = 0;
        this.size = 0;
        this.consumerOffsetMap = new ConcurrentHashMap<>();
    }

    public boolean push(KmqMessage<T> value) {
        if (size == limit || pushIndex == limit) {
            return false;
        }
        size++;
        this.arr[pushIndex] = value;
        this.pushIndex = nextIndex(this.pushIndex);
        return true;
    }

    public KmqMessage<T> poll(String consumerId) {
        if (size == 0) return null;
        if (consumerOffsetMap.getOrDefault(consumerId, 0) == this.pushIndex) return null;
        int pollIndex = getConsumerPollIndex(consumerId);
        KmqMessage<T> value = this.arr[pollIndex];
        setConsumerPollIndex(consumerId, pollIndex);
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int nextIndex(int index) {
        return index + 1;
    }

    private int getConsumerPollIndex(String consumerId) {
        return consumerOffsetMap.getOrDefault(consumerId, 0);
    }

    private void setConsumerPollIndex(String consumerId, int pollIndex) {
        int nextIndex = nextIndex(pollIndex);
        consumerOffsetMap.put(consumerId, nextIndex);
    }

    public void print() {
        for (KmqMessage<T> tKmqMessage : arr) {
            System.out.println(tKmqMessage);
        }
    }

}