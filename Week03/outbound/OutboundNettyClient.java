// package com.maccoy.advanced.gateway;
//
// import io.netty.bootstrap.Bootstrap;
// import io.netty.bootstrap.ServerBootstrap;
// import io.netty.buffer.PooledByteBufAllocator;
// import io.netty.channel.Channel;
// import io.netty.channel.ChannelFuture;
// import io.netty.channel.ChannelFutureListener;
// import io.netty.channel.ChannelInitializer;
// import io.netty.channel.ChannelOption;
// import io.netty.channel.ChannelPipeline;
// import io.netty.channel.EventLoopGroup;
// import io.netty.channel.epoll.EpollChannelOption;
// import io.netty.channel.nio.NioEventLoopGroup;
// import io.netty.channel.socket.SocketChannel;
// import io.netty.channel.socket.nio.NioServerSocketChannel;
// import io.netty.channel.socket.nio.NioSocketChannel;
// import io.netty.handler.codec.http.HttpObjectAggregator;
// import io.netty.handler.codec.http.HttpServerCodec;
// import io.netty.handler.logging.LogLevel;
// import io.netty.handler.logging.LoggingHandler;
//
// public class NettyHttpClient {
//
//     private final String host;
//
//     private final int port;
//
//     private Channel channel;
//
//     public NettyHttpClient(String host, int port) {
//         this.host = host;
//         this.port = port;
//     }
//
//     public void start() throws InterruptedException {
//         EventLoopGroup group = new NioEventLoopGroup();
//         Bootstrap bootstrap = new Bootstrap();
//         bootstrap.group(group).channel(NioSocketChannel.class)
//                 .handler(new ChannelInitializer<SocketChannel>() {
//                     @Override
//                     protected void initChannel(SocketChannel ch) throws Exception {
//                         ChannelPipeline pipeline = ch.pipeline();
//                         pipeline.addLast(new HttpServerCodec());
//                         pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
//                         pipeline.addLast(new ClientHandler());
//                     }
//                 });
//         ChannelFuture future = bootstrap.connect(host, port).sync();
//
//         future.addListener(new ChannelFutureListener()  {
//             @Override
//             public void operationComplete(ChannelFuture channelFuture) throws Exception {
//                 if (future.isSuccess()) {
//                     System.out.println("连接服务器成功");
//                 } else {
//                     System.out.println("连接服务器失败");
//                     future.cause().printStackTrace();
//                     group.shutdownGracefully(); //关闭线程组
//                 }
//             }
//         });
//
//         this.channel = future.channel();
//     }
//
//     public Channel getChannel() {
//         return channel;
//     }
//
//     public static void main(String[] args) {
//         try {
//             NettyHttpClient client = new NettyHttpClient("127.0.0.1", 9999);
//             client.start();
//             Channel channel = client.getChannel();
//             channel.writeAndFlush("test");
//         } catch (InterruptedException e) {
//             e.printStackTrace();
//         }
//     }
// }
