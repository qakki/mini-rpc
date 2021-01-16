package com.qakki.rpc.transport;

/**
 * 服务端
 * @author qakki
 * @date 2021/1/16 6:02 下午
 **/
public interface TransportServer {

    void init(int port, RequestHandler handler);

    void start();

    void stop();

}
