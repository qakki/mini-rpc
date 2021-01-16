package com.qakki.rpc.common.codec;

/**
 * 序列化
 * @author qakki
 * @date 2021/1/16 5:34 下午
 */
public interface Encoder {

    <T> byte[] encode(T obj);

}
