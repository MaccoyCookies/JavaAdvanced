package com.maccoy.advanced.gateway.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {


    private List<String> proxyServers;

    public HttpInboundInitializer(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        // p.addLast(new HttpServerCodec());
        p.addLast("decoder",new HttpRequestDecoder());
        p.addLast("encoder", new HttpResponseEncoder());
        //p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HttpObjectAggregator(1024 * 1024 * 1024));
        p.addLast(new HttpInboundHandler(proxyServers));
    }
}
