package com.maccoy.rpc.core.frame;

import com.alibaba.fastjson.JSON;
import com.maccoy.rpc.core.domain.RpcProtocol;
import com.maccoy.rpc.core.domain.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcNettyClientHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    private RpcResponse response;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) {
        log.info("Netty client receive message:");
        log.info("Message length: " + msg.getLen());
        log.info("Message content: " + new String(msg.getData(), CharsetUtil.UTF_8));

        // 将 RpcResponse字符串 反序列化成 RpcResponse对象
        RpcResponse rpcfxResponse = JSON.parseObject(new String(msg.getData(), CharsetUtil.UTF_8),
                RpcResponse.class);
        log.info("Netty client serializer : " + rpcfxResponse.toString());

        response = rpcfxResponse;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 阻塞等待结果后返回
     * @return 后台服务器响应
     * @throws InterruptedException exception
     */
    RpcResponse getResponse() {
        return response;
    }

}
