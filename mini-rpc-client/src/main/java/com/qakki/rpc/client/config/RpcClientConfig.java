package com.qakki.rpc.client.config;

import com.qakki.rpc.Peer;
import com.qakki.rpc.common.codec.Decoder;
import com.qakki.rpc.common.codec.Encoder;
import com.qakki.rpc.common.codec.json.JSONDecoder;
import com.qakki.rpc.common.codec.json.JSONEncoder;
import com.qakki.rpc.transport.TransportClient;
import com.qakki.rpc.transport.http.HTTPTransportClient;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 配置
 * @author qakki
 * @date 2021/1/16 8:35 下午
 */
@Data
public class RpcClientConfig {

    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;
    private int connectCount = 1;
    private List<Peer> servers = Collections.singletonList(new Peer("127.0.0.1", 3000));

}
