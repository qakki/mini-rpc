package com.qakki.rpc.transport.netty;

import com.alibaba.fastjson.JSON;
import com.qakki.rpc.common.codec.Decoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/4 11:04 下午
 */
public class RpcDecoder<T> extends ByteToMessageDecoder {

    Class<T> clazz;
    Decoder decoder;

    public RpcDecoder(Class<T> clazz, Decoder decoder) {
        this.clazz = clazz;
        this.decoder = decoder;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //因为之前编码的时候写入一个Int型，4个字节来表示长度
        if (byteBuf.readableBytes() < 4) {
            return;
        }
        //标记当前读的位置
        byteBuf.markReaderIndex();
        int dataLength = byteBuf.readInt();
        if (byteBuf.readableBytes() < dataLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        //将byteBuf中的数据读入data字节数组
        byteBuf.readBytes(data);
        Object o = JSON.parseObject(data, clazz);
        list.add(o);
    }

}
