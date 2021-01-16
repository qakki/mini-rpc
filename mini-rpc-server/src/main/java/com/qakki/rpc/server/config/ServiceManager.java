package com.qakki.rpc.server.config;

import com.qakki.rpc.Request;
import com.qakki.rpc.ServiceDescriptor;
import com.qakki.rpc.common.utils.ReflectionUtils;
import com.qakki.rpc.server.domain.ServiceInstance;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qakki
 * @date 2021/1/16 7:44 下午
 */
@Slf4j
public class ServiceManager {

    private Map<ServiceDescriptor, ServiceInstance> serviceMap = new ConcurrentHashMap<>();

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);

        for (Method method : methods) {
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(interfaceClass, method);
            serviceMap.put(serviceDescriptor, serviceInstance);
            log.info("service register interface={}, method{}", interfaceClass.getName(), method.getName());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor serviceDescriptor = request.getService();
        return serviceMap.get(serviceDescriptor);
    }
}
