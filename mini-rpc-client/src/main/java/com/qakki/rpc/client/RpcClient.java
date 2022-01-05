package com.qakki.rpc.client;

import com.qakki.rpc.client.config.RpcClientConfig;
import com.qakki.rpc.client.handler.ClientHandler;
import com.qakki.rpc.client.invoke.RemoteInvoker;
import com.qakki.rpc.transport.TransportClient;
import com.qakki.rpc.transport.netty.NettyTransportClient;

import java.lang.reflect.Proxy;

/**
 * 配置
 *
 * @author qakki
 * @date 2021/1/16 8:39 下午
 */
public class RpcClient {

//    private final Encoder encoder;
//    private final Decoder decoder;
//    private final TransportSelector selector;

    private final TransportClient transportClient;

    public RpcClient(RpcClientConfig clientConfig) {
//        encoder = ReflectionUtils.newInstance(clientConfig.getEncoderClass());
//        decoder = ReflectionUtils.newInstance(clientConfig.getDecoderClass());
//        selector = ReflectionUtils.newInstance(clientConfig.getSelectorClass());
//
//        selector.init(clientConfig.getServers(), clientConfig.getConnectCount(), clientConfig.getTransportClass());
        transportClient = new NettyTransportClient(new ClientHandler());
        transportClient.connect(clientConfig.getServers().get(0));
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, transportClient)
        );
    }
}
