package com.qakki.rpc.common.codec.json;

import com.alibaba.fastjson.JSON;
import com.qakki.rpc.common.codec.Encoder;

/**
 * json序列化
 * @author qakki
 * @date 2021/1/16 5:39 下午
 */
public class JSONEncoder implements Encoder {

    @Override
    public <T> byte[] encode(T obj) {
        return JSON.toJSONBytes(obj);
    }
}
