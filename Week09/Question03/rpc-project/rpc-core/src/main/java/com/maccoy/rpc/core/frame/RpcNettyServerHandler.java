package com.maccoy.rpc.core.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.maccoy.rpc.core.domain.RpcProtocol;
import com.maccoy.rpc.core.domain.RpcRequest;
import com.maccoy.rpc.core.domain.RpcResponse;
import com.maccoy.rpc.core.exception.BaseException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class RpcNettyServerHandler extends SimpleChannelInboundHandler<RpcProtocol> {

    private ApplicationContext applicationContext;

    RpcNettyServerHandler(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol msg) throws Exception {
        log.info("Netty server receive message:");
        log.info("Message length: " + msg.getLen());
        log.info("Message content: " + new String(msg.getData(), CharsetUtil.UTF_8));

        // 获取 RpcProtocol中的 RpcRequest内容，反序列化成 RpcRequest 对象
        RpcRequest rpcRequest = JSON.parseObject(new String(msg.getData(), CharsetUtil.UTF_8),
                RpcRequest.class);
        log.info("Netty server serializer : " + rpcRequest.toString());

        // 获取相应的bean，反射调用方法，获取结果
        RpcResponse response = invoke(rpcRequest);

        // 返回结果给netty 客户端
        RpcProtocol message = new RpcProtocol();
        String requestJson = JSON.toJSONString(response);
        message.setLen(requestJson.getBytes(CharsetUtil.UTF_8).length);
        message.setData(requestJson.getBytes(CharsetUtil.UTF_8));

        channelHandlerContext.writeAndFlush(message).sync();
        log.info("return response to client end");
    }

    /**
     * 获取接口实现对应的bean，反射调用方法，返回结果
     *
     * @param request rpc request
     * @return result
     */
    private RpcResponse invoke(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        String serviceClass = request.getServiceClass();

        // 作业1：改成泛型和反射
        Object service = resolver(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams());
            log.info("Server method invoke result: " + result.toString());
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            log.info("Server Response serialize to string return");
            return response;
        } catch (IllegalAccessException | InvocationTargetException | BaseException e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Object resolver(String serviceClass) {
        try {
            Class<?> aClass = Class.forName(serviceClass);
            Map<String, ?> beansOfType = applicationContext.getBeansOfType(aClass);
            for (Map.Entry<String, ?> stringEntry : beansOfType.entrySet()) {
                return stringEntry.getValue();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().orElse(null);
    }
}
