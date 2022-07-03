package com.maccoy.rpc.core.common;

import com.maccoy.rpc.core.domain.RpcProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class RpcDecoder extends ByteToMessageDecoder {

    private int length = 0;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() >= 4) {
            if (length == 0) {
                length = in.readInt();
            }
            if (in.readableBytes() < length) {
                return;
            }
            byte[] content = new byte[length];
            if (in.readableBytes() >= length) {
                in.readBytes(content);
                RpcProtocol rpcProtocol = new RpcProtocol();
                rpcProtocol.setLen(length);
                rpcProtocol.setData(content);
                out.add(rpcProtocol);
            }
            length = 0;
        }
    }

}
