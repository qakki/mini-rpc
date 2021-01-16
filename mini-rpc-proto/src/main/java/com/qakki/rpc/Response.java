package com.qakki.rpc;

import lombok.Data;

/**
 * 返回
 * @author qakki
 * @date 2021/1/16 4:38 下午
 */
@Data
public class Response {

    /**
     * 编码
     */
    private int code;

    /**
     * 信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private Object data;
}
