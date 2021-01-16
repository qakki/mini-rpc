package com.qakki.rpc.client.config;

import com.qakki.rpc.Peer;
import com.qakki.rpc.transport.TransportClient;

import java.util.List;

/**
 * 路由选择
 * @author qakki
 * @date 2021/1/16 8:21 下午
 */
public interface TransportSelector {

    /**
     * 初始化selector
     * @param peers 端点信息
     * @param count 连接限制
     * @param clazz 实现clazz
     */
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择client交互
     * @return client
     */
    TransportClient select();

    /**
     * 释放client
     * @param client client
     */
    void release(TransportClient client);

    /**
     * 关闭连接
     */
    void close();

}
