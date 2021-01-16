package com.qakki.rpc.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求
 * @author qakki
 * @date 2021/1/16 6:04 下午
 **/
public interface RequestHandler {

    void onRequest(InputStream receive, OutputStream response);

}
