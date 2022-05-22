// package com.maccoy.advanced.gateway;
//
// import io.netty.channel.ChannelInitializer;
// import io.netty.channel.ChannelPipeline;
// import io.netty.channel.socket.SocketChannel;
// import io.netty.handler.codec.http.HttpObjectAggregator;
// import io.netty.handler.codec.http.HttpServerCodec;
//
// public class ClientInitializer extends ChannelInitializer<SocketChannel> {
//
//     @Override
//     public void initChannel(SocketChannel ch) {
//         ChannelPipeline pipeline = ch.pipeline();
//         pipeline.addLast(new HttpServerCodec());
//         pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
//         pipeline.addLast(new ClientHandler());
//     }
// }
