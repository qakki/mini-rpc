package com.qakki.rpc.transport;

import com.qakki.rpc.Peer;

import java.io.InputStream;

/**
 * 客户端连接
 * @author qakki
 * @date 2021/1/16 6:01 下午
 **/
public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream data);

    void close();

}
