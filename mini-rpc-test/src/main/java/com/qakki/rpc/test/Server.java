package com.qakki.rpc.test;

import com.qakki.rpc.server.RpcServer;
import com.qakki.rpc.server.config.RpcServerConfig;
import com.qakki.rpc.test.service.TestService;
import com.qakki.rpc.test.service.TestServiceImpl;

/**
 * 服务端
 * @author qakki
 * @date 2021/1/16 9:04 下午
 */
public class Server {

    public static void main(String[] args) {
        RpcServer server = new RpcServer(new RpcServerConfig());
        server.register(TestService.class, new TestServiceImpl());
        server.start();
        
        while (true) {
            try {
                server.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
