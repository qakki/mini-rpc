package com.qakki.rpc.server.config;

import com.qakki.rpc.common.codec.Decoder;
import com.qakki.rpc.common.codec.Encoder;
import com.qakki.rpc.common.codec.json.JSONDecoder;
import com.qakki.rpc.common.codec.json.JSONEncoder;
import com.qakki.rpc.transport.TransportServer;
import com.qakki.rpc.transport.http.HTTPTransportServer;
import lombok.Data;

/**
 * 配置
 * @author qakki
 * @date 2021/1/16 6:39 下午
 */
@Data
public class RpcServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = 3000;

}
