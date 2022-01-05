package com.qakki.rpc.transport;

import com.qakki.rpc.Response;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/5 8:24 上午
 **/
public interface ResponseHandler {
    Response getRpcResponse(String requestId);
}
