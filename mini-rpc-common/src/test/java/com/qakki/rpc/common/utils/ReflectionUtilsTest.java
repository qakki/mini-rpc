package com.qakki.rpc.common.utils;

import junit.framework.TestCase;
import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectionUtilsTest extends TestCase {

    @Test
    public void testNewInstance() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        assertNotNull(t);
    }

    @Test
    public void testGetPublicMethods() {
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(methods.length, 1);
        assertEquals(methods[0].getName(), "c");
    }

    @Test
    public void testInvoke() {
        TestClass t = ReflectionUtils.newInstance(TestClass.class);
        Method[] methods = ReflectionUtils.getPublicMethods(TestClass.class);
        String res = (String) ReflectionUtils.invoke(t, methods[0]);
        assertEquals(res, "c");
    }
}