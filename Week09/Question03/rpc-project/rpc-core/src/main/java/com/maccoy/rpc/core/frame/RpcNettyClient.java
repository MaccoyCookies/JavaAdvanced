package com.maccoy.rpc.core.frame;

import com.alibaba.fastjson.JSON;
import com.maccoy.rpc.core.common.RpcDecoder;
import com.maccoy.rpc.core.common.RpcEncoder;
import com.maccoy.rpc.core.domain.RpcProtocol;
import com.maccoy.rpc.core.domain.RpcRequest;
import com.maccoy.rpc.core.domain.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class RpcNettyClient {

    private final EventLoopGroup clientGroup = new NioEventLoopGroup(16);

    private static class SingleClass {
        private final static RpcNettyClient INSTANCE = new RpcNettyClient();
    }

    public static RpcNettyClient getInstance() {
        return SingleClass.INSTANCE;
    }


    /**
     * 调用channel发送请求，从handler中获取响应结果
     * @return 响应
     * @throws InterruptedException exception
     */
    public RpcResponse getResponse(RpcRequest rpcRequest, String url) throws InterruptedException,
            URISyntaxException {
        RpcProtocol request = convertNettyRequest(rpcRequest);

        URI uri = new URI(url);

        // 没有或者不可用则新建
        // 并将最终的handler添加到pipeline中，拿到结果后返回
        RpcNettyClientHandler handler = new RpcNettyClientHandler();

        Channel channel = createChannel(uri.getHost(), uri.getPort());
        channel.pipeline().replace("clientHandler", "clientHandler", handler);

        channel.writeAndFlush(request).sync();
        return handler.getResponse();
    }

    /**
     * 返回新的Channel
     * @param address ip地址
     * @param port 端口
     * @return channel
     * @throws InterruptedException exception
     */
    private Channel createChannel(String address, int port) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientGroup)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.AUTO_CLOSE, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("Message Encoder", new RpcEncoder());
                        pipeline.addLast("Message Decoder", new RpcDecoder());
                        pipeline.addLast("Message Handler", new RpcNettyClientHandler());
                    }
                });
        return bootstrap.connect(address, port).sync().channel();
    }

    /**
     * 将 {@RpcRequest} 转成 netty 自定义的通信格式 {@RpcProtocol}
     * @param rpcRequest RpcRequest
     * @return RpcProtocol
     */
    private RpcProtocol convertNettyRequest(RpcRequest rpcRequest) {
        RpcProtocol request = new RpcProtocol();
        String requestJson = JSON.toJSONString(rpcRequest);
        request.setLen(requestJson.getBytes(CharsetUtil.UTF_8).length);
        request.setData(requestJson.getBytes(CharsetUtil.UTF_8));
        return request;
    }

    /**
     * 关闭线程池
     */
    public void destroy() {
        clientGroup.shutdownGracefully();
    }



}
