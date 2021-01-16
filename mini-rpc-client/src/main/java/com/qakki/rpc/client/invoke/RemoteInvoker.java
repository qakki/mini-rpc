package com.qakki.rpc.client.invoke;

import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.ServiceDescriptor;
import com.qakki.rpc.client.config.TransportSelector;
import com.qakki.rpc.common.codec.Decoder;
import com.qakki.rpc.common.codec.Encoder;
import com.qakki.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 * @author qakki
 * @date 2021/1/16 8:47 下午
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private final Class<?> clazz;
    private final Encoder encoder;
    private final Decoder decoder;
    private final TransportSelector selector;

    public RemoteInvoker(Class<?> clazz, Encoder encoder, Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        Response response = invokeRemote(request);
        if (response.getCode() != 0) {
            throw new Exception("invoke failed!");
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response = null;
        try {
            client = selector.select();
            byte[] outBytes = encoder.encode(request);

            InputStream in = client.write(new ByteArrayInputStream(outBytes));
            byte[] bytes = IOUtils.readFully(in, in.available());
            response = decoder.decode(bytes, Response.class);
        } catch (Exception e) {
            log.error("invoke error" + e);
            response = new Response();
            response.setCode(-1);
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        return response;
    }
}
