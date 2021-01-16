package com.qakki.rpc.server.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 服务
 * @author qakki
 * @date 2021/1/16 7:44 下午
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    private Object target;
    private Method method;
}
