package com.qakki.rpc.test.service;

/**
 * 测试
 * @author qakki
 * @date 2021/1/16 9:03 下午
 */
public class TestServiceImpl implements TestService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}
