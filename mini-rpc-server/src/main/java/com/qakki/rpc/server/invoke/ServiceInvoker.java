package com.qakki.rpc.server.invoke;

import com.qakki.rpc.Request;
import com.qakki.rpc.common.utils.ReflectionUtils;
import com.qakki.rpc.server.domain.ServiceInstance;

/**
 * 服务调用
 * @author qakki
 * @date 2021/1/16 8:00 下午
 */
public class ServiceInvoker {

    /**
     * 方法调用
     *
     * @param instance 实例
     * @param request  请求
     * @return 返回
     */
    public static Object invoke(ServiceInstance instance, Request request) {
        return ReflectionUtils.invoke(instance.getTarget(), instance.getMethod(), request.getParameters());
    }

}
