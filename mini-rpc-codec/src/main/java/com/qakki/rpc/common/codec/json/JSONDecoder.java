package com.qakki.rpc.common.codec.json;

import com.alibaba.fastjson.JSON;
import com.qakki.rpc.common.codec.Decoder;

/**
 * json反序列化
 * @author qakki
 * @date 2021/1/16 5:39 下午
 */
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
