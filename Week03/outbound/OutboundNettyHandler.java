// package com.maccoy.advanced.gateway;
//
// import io.netty.channel.ChannelHandlerContext;
// import io.netty.channel.SimpleChannelInboundHandler;
// import io.netty.handler.codec.http.FullHttpResponse;
//
// public class ClientHandler extends SimpleChannelInboundHandler {
//
//     @Override
//     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//         FullHttpResponse response = (FullHttpResponse) msg;
//         System.out.println("response.headers(): " + response.headers());
//         System.out.println("response.content().toString()" + response.content().toString());
//     }
//
//     @Override
//     protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//         FullHttpResponse response = (FullHttpResponse) msg;
//         System.out.println("response.headers(): " + response.headers());
//         System.out.println("response.content().toString()" + response.content().toString());
//     }
//
//     @Override
//     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//         ctx.close();
//     }
// }
