package com.qakki.rpc.common.codec.json;

import junit.framework.TestCase;

import java.util.Arrays;

public class JSONEncoderTest extends TestCase {

    public void testEncode() {
        JSONEncoder encoder = new JSONEncoder();
        JSONDecoder decoder = new JSONDecoder();

        BeanTest bean = new BeanTest();
        bean.setA(0);
        bean.setB("");
        bean.setC(0L);
        bean.setD('c');

        byte[] bytes = encoder.encode(bean);
        System.out.println(Arrays.toString(bytes));
        BeanTest obj = decoder.decode(bytes, BeanTest.class);

        assertEquals(bean, obj);
    }
}