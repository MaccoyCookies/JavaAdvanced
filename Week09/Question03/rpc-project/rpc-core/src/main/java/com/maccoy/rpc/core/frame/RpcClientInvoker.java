package com.maccoy.rpc.core.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.maccoy.rpc.core.domain.RpcRequest;
import com.maccoy.rpc.core.domain.RpcResponse;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;

public class RpcClientInvoker {

    static {
        ParserConfig.getGlobalInstance().addAccept("com.maccoy");
    }

    public static <T> T create(final Class<T> serviceClass, final String url) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(), new Class[]{serviceClass}, new RpcClientInvocationHandler(serviceClass, url));
    }

    public static class RpcClientInvocationHandler implements InvocationHandler {

        private final Class<?> serviceClass;
        private final String url;

        public <T> RpcClientInvocationHandler(Class<T> serviceClass, String url) {
            this.serviceClass = serviceClass;
            this.url = url;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RpcRequest rpcRequest = RpcRequest.builder()
                    .params(args)
                    .serviceClass(this.serviceClass.getName())
                    .method(method.getName())
                    .build();
            RpcResponse response = post(rpcRequest, url);
            // 序列化成对象返回
            return JSON.parse(response.getResult().toString());
        }

        private RpcResponse post(RpcRequest req, String url) {
            RpcResponse rpcResponse;
            try {
                rpcResponse = RpcNettyClient.getInstance().getResponse(req, url);
            } catch (InterruptedException | URISyntaxException e) {
                e.printStackTrace();
                return null;
            }
            assert rpcResponse != null;
            if (!rpcResponse.isStatus()) {
                rpcResponse.getException().printStackTrace();
                return null;
            }
            return rpcResponse;
        }
    }

}
