package com.qakki.rpc.transport;

import com.qakki.rpc.Peer;
import com.qakki.rpc.Request;
import com.qakki.rpc.Response;

/**
 * 客户端连接
 * @author qakki
 * @date 2021/1/16 6:01 下午
 **/
public interface TransportClient {

    void connect(Peer peer);

    Response write(Request request);

    void close();

}
