package com.qakki.rpc;

import lombok.Data;

/**
 * 请求类
 *
 * @author qakki
 * @date 2021/1/16 4:37 下午
 */
@Data
public class Request {

    private String requestId;

    /**
     * 请求
     */
    private ServiceDescriptor service;

    /**
     * 相应
     */
    private Object[] parameters;
}
