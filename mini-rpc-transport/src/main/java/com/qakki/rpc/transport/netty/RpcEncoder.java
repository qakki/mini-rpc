package com.qakki.rpc.transport.netty;

import com.alibaba.fastjson.JSON;
import com.qakki.rpc.common.codec.Encoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/4 10:54 下午
 */
public class RpcEncoder extends MessageToByteEncoder {

    Class clazz;
    Encoder encoder;

    public RpcEncoder(Class clazz, Encoder encoder) {
        this.clazz = clazz;
        this.encoder = encoder;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object t, ByteBuf byteBuf) throws Exception {
        byte[] bytes = JSON.toJSONBytes(t);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

}
