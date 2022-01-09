package com.qakki.rpc.common.codec.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.qakki.rpc.common.codec.Decoder;

import java.io.ByteArrayInputStream;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/9 4:06 下午
 */
public class HessianDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        T result = null;
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            Hessian2Input input = new Hessian2Input(is);
            result = (T) input.readObject(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
