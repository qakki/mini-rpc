package com.qakki.rpc.test;

import com.qakki.rpc.client.RpcClient;
import com.qakki.rpc.client.config.RpcClientConfig;
import com.qakki.rpc.test.service.TestService;

/**
 * 客服端
 * @author qakki
 * @date 2021/1/16 9:04 下午
 */
public class Client {

    public static void main(String[] args) {
        RpcClient client = new RpcClient(new RpcClientConfig());
        TestService proxy = client.getProxy(TestService.class);

        int add = proxy.add(1, 2);
        int minus = proxy.minus(1, 2);
        System.out.println(add);
        System.out.println(minus);
    }

}
