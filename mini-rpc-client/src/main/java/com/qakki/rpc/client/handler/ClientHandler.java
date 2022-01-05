package com.qakki.rpc.client.handler;

import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.client.DefaultFuture;
import com.qakki.rpc.transport.ResponseHandler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/5 8:01 上午
 */
public class ClientHandler extends ChannelDuplexHandler implements ResponseHandler {

    private final Map<String, DefaultFuture> map = new ConcurrentHashMap<>();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Response) {
            Response response = (Response) msg;
            DefaultFuture defaultFuture = map.get(response.getRequestId());
            defaultFuture.setResponse(response);
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Request) {
            Request request = (Request) msg;
            DefaultFuture defaultFuture = new DefaultFuture();
            map.put(request.getRequestId(), defaultFuture);
        }
        super.write(ctx, msg, promise);
    }

    @Override
    public Response getRpcResponse(String requestId) {
        DefaultFuture defaultFuture = map.get(requestId);
        return defaultFuture.getRpcResponse(5000);
    }

}
