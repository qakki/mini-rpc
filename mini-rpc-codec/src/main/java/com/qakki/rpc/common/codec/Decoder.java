package com.qakki.rpc.common.codec;

/**
 * 反序列化
 * @author qakki
 * @date 2021/1/16 5:35 下午
 */
public interface Decoder {

    /**
     * 反序列化
     * @param bytes 字节
     * @param clazz 类
     * @param <T>   对象
     * @return 实体类
     */
    <T> T decode(byte[] bytes, Class<T> clazz);
    
}
