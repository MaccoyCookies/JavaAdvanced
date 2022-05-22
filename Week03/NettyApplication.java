package com.maccoy.advanced.gateway;

import com.maccoy.advanced.gateway.inbound.HttpInboundServer;

import java.util.Arrays;
import java.util.List;

public class NettyApplication {

    private static final List<String> proxyServers = Arrays.asList("http://localhost:8001", "http://localhost:8002");

    public static void main(String[] args) {
        try {
            HttpInboundServer httpInboundServer = new HttpInboundServer(proxyServers);
            httpInboundServer.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
