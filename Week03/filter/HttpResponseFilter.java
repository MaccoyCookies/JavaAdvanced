package com.maccoy.advanced.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HttpResponseFilter {

    void filter(FullHttpResponse fullHttpResponse);

}
