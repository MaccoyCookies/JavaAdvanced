package com.maccoy.advanced.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalanceHttpEndpointRouter implements HttpEndPointRouter {

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        int index = atomicInteger.getAndIncrement() % size;
        if (atomicInteger.get() == 99999) atomicInteger.set(index + 1);
        return endpoints.get(index);
    }
}
