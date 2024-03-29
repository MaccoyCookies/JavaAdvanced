package com.maccoy.rpc.core.frame;

import com.maccoy.rpc.core.common.RpcDecoder;
import com.maccoy.rpc.core.common.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RpcNettyServer {

    private final ApplicationContext context;

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public RpcNettyServer(ApplicationContext context) {
        this.context = context;
    }

    public void destroy() {
        worker.shutdownGracefully();
        boss.shutdownGracefully();
    }

    public void run() throws Exception {
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("Message Encoder", new RpcEncoder());
                        pipeline.addLast("Message Decoder", new RpcDecoder());
                        pipeline.addLast("Message Handler", new RpcNettyServerHandler(context));
                    }
                });

        int port = 8080;
        Channel channel = serverBootstrap.bind(port).sync().channel();
        log.info("Netty server listen in port: " + port);
        channel.closeFuture().sync();
    }
}
