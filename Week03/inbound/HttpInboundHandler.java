package com.maccoy.advanced.gateway.inbound;

import com.maccoy.advanced.gateway.filter.HeaderHttpRequestFilter;
import com.maccoy.advanced.gateway.filter.HttpRequestFilter;
import com.maccoy.advanced.gateway.outbound.OutboundOkHttpHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ReferenceCountUtil;

import java.util.List;


public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final HttpRequestFilter httpRequestFilter = new HeaderHttpRequestFilter();
    private final OutboundOkHttpHandler okHttpHandler;

    public HttpInboundHandler(List<String> proxyServers) {
        this.okHttpHandler = new OutboundOkHttpHandler(proxyServers);
    }

    /**
     * 通知处理器最后的channelRead()是当前批处理中的最后一条消息时调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 接受发送的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof LastHttpContent) {
                FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
                System.out.println(fullHttpRequest.headers().toString());
                httpRequestFilter.filter(fullHttpRequest);
                okHttpHandler.handle(fullHttpRequest, ctx);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    /**
     * 读操作时捕获到异常时调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) ctx.close();
    }
}
