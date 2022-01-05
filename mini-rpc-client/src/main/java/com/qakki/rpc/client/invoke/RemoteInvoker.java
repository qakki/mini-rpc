package com.qakki.rpc.client.invoke;

import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.ServiceDescriptor;
import com.qakki.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 动态代理
 *
 * @author qakki
 * @date 2021/1/16 8:47 下午
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private final Class<?> clazz;
    //    private final Encoder encoder;
//    private final Decoder decoder;
//    private final TransportSelector selector;
    private final TransportClient client;

    public RemoteInvoker(Class<?> clazz, TransportClient client) {
        this.clazz = clazz;
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setRequestId(UUID.randomUUID().toString());
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = invokeRemote(request);
        if (response.getCode() != 0) {
            throw new Exception("invoke failed!");
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
//        TransportClient client = null;
        Response response = null;
        try {
            //        client = selector.select();
//            byte[] bytes = client.write(request);
//            response = decoder.decode(bytes, Response.class);

            response = client.write(request);
        } catch (Exception e) {
            log.error("invoke error" + e);
            response = new Response();
            response.setCode(-1);
        } finally {
//            if (client != null) {
//                selector.release(client);
//            }
        }
        return response;
    }
}
