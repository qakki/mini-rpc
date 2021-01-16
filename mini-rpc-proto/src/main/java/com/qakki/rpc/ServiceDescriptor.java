package com.qakki.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
