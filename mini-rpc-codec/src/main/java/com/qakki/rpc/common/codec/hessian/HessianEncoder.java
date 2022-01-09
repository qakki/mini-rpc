package com.qakki.rpc.common.codec.hessian;

import com.caucho.hessian.io.Hessian2Output;
import com.qakki.rpc.common.codec.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/9 4:06 下午
 */
public class HessianEncoder implements Encoder {
    @Override
    public <T> byte[] encode(T obj) {
        byte[] res = null;
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            Hessian2Output out = new Hessian2Output(bs);
            out.writeObject(obj);
            out.flush();
            res = bs.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
