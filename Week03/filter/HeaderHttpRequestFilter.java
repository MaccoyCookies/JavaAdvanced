package com.maccoy.advanced.gateway.filter;

import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullHttpRequest) {
        fullHttpRequest.headers().set("gateway", "request");
    }
}
