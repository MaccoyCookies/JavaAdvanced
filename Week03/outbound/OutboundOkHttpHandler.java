package com.maccoy.advanced.gateway.outbound;

import com.maccoy.advanced.gateway.filter.HeaderHttpResponseFilter;
import com.maccoy.advanced.gateway.filter.HttpResponseFilter;
import com.maccoy.advanced.gateway.router.HttpEndPointRouter;
import com.maccoy.advanced.gateway.router.LoadBalanceHttpEndpointRouter;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

public class OutboundOkHttpHandler {

    private static final Logger logger = LoggerFactory.getLogger(OutboundOkHttpHandler.class);

    private final List<String> proxyServers;
    private final HttpResponseFilter httpResponseFilter = new HeaderHttpResponseFilter();
    private final HttpEndPointRouter httpEndPointRouter = new LoadBalanceHttpEndpointRouter();

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10L, TimeUnit.SECONDS)
            .readTimeout(10L, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(1500,5L, TimeUnit.MINUTES))
            .build();

    public OutboundOkHttpHandler(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    public void handle(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx) {
        String route = httpEndPointRouter.route(proxyServers);
        String url = route + fullHttpRequest.uri();
        String response = httpGet(url, fullHttpRequest);
        handleResponse(fullHttpRequest, ctx, response);
    }

    private void handleResponse(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx, String response) {
        FullHttpResponse fullHttpResponse = null;
        try {
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(response.getBytes("UTF-8")));
            httpResponseFilter.filter(fullHttpResponse);
            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().set("Content-Length", fullHttpResponse.content().readableBytes());
        } catch (Exception e) {
            logger.error("error: ", e);
            fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        } finally {
            if (fullHttpRequest != null) {
                if (HttpUtil.isKeepAlive(fullHttpRequest)) {
                    fullHttpResponse.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(fullHttpResponse);
                } else {
                    ctx.write(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    private String httpGet(String url, FullHttpRequest fullHttpRequest) {
        try {
            Request request = new Request.Builder()
                    .header("gateway", fullHttpRequest.headers().get("gateway"))
                    .url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return "error";
    }
}
