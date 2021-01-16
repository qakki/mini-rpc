package com.qakki.rpc.server;

import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.common.codec.Decoder;
import com.qakki.rpc.common.codec.Encoder;
import com.qakki.rpc.common.utils.ReflectionUtils;
import com.qakki.rpc.server.config.RpcServerConfig;
import com.qakki.rpc.server.config.ServiceManager;
import com.qakki.rpc.server.domain.ServiceInstance;
import com.qakki.rpc.server.invoke.ServiceInvoker;
import com.qakki.rpc.transport.RequestHandler;
import com.qakki.rpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 请求配置
 * @author qakki
 * @date 2021/1/16 8:03 下午
 */
@Slf4j
public class RpcServer {

    private final TransportServer net;
    private final Encoder encoder;
    private final Decoder decoder;
    private final ServiceManager serviceManager;
    private final ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config) {
        net = ReflectionUtils.newInstance(config.getTransportClass());
        encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceInvoker = new ServiceInvoker();
        this.serviceManager = new ServiceManager();
        net.init(config.getPort(), handler);
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream response) {
            Response res = new Response();
            try {
                byte[] bytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(bytes, Request.class);
                log.info("receive request:{}", request);

                ServiceInstance instance = serviceManager.lookup(request);
                Object obj = serviceInvoker.invoke(instance, request);
                res.setData(obj);
                res.setCode(0);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                res.setCode(-1);
                res.setMessage("失败" + e.getMessage());
            } finally {
                byte[] bytes = encoder.encode(res);
                try {
                    response.write(bytes);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    };

}
