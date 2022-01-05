package com.qakki.rpc.client;

import com.qakki.rpc.Response;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/5 8:10 上午
 */
public class DefaultFuture {
    private Response rpcResponse;
    private volatile boolean isSucceed = false;
    private final Object object = new Object();

    public Response getRpcResponse(int timeout) {
        synchronized (object) {
            while (!isSucceed) {
                try {
                    object.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return rpcResponse;
        }
    }

    public void setResponse(Response response) {
        if (isSucceed) {
            return;
        }
        synchronized (object) {
            this.rpcResponse = response;
            this.isSucceed = true;
            object.notify();
        }
    }

}
