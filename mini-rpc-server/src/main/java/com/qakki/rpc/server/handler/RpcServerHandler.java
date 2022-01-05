package com.qakki.rpc.server.handler;

import com.alibaba.fastjson.JSON;
import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.server.config.ServiceManager;
import com.qakki.rpc.server.domain.ServiceInstance;
import com.qakki.rpc.transport.RequestHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import static com.qakki.rpc.server.invoke.ServiceInvoker.invoke;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/4 11:36 下午
 */
@Slf4j
@ChannelHandler.Sharable
public class RpcServerHandler extends SimpleChannelInboundHandler<Request> implements RequestHandler {

    private final ServiceManager serviceManager;

    public RpcServerHandler(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        Response response = new Response();
        log.info("receive request, request={}", JSON.toJSONString(request));
        try {
            ServiceInstance instance = serviceManager.lookup(request);
            Object obj = invoke(instance, request);
            response.setRequestId(request.getRequestId());
            response.setData(obj);
            response.setCode(0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setCode(-1);
            response.setMessage("失败" + e.getMessage());
        }
        channelHandlerContext.writeAndFlush(response);
    }

}
