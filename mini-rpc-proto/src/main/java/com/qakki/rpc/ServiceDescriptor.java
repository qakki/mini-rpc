package com.qakki.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 服务
 * @author qakki
 * @date 2021/1/16 4:33 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {

    /**
     * 类
     */
    private String clazz;

    /**
     * 方法
     */
    private String method;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 参数类型
     */
    private String[] parameterTypes;

    public static ServiceDescriptor from(Class<?> clazz, Method method) {
        ServiceDescriptor sdp = new ServiceDescriptor();
        sdp.setClazz(clazz.getName());
        sdp.setMethod(method.getName());
        sdp.setReturnType(method.getReturnType().getTypeName());
        sdp.setParameterTypes(Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new));
        return sdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceDescriptor)) {
            return false;
        }
        ServiceDescriptor that = (ServiceDescriptor) o;

        if (!Objects.equals(clazz, that.clazz)) {
            return false;
        }
        if (!Objects.equals(method, that.method)) {
            return false;
        }
        if (!Objects.equals(returnType, that.returnType)) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        int result = clazz != null ? clazz.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(parameterTypes);
        return result;
    }
}
