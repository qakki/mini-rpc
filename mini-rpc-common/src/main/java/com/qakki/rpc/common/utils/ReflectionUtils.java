package com.qakki.rpc.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * 工具类
 * @author qakki
 * @date 2021/1/16 5:06 下午
 */
public class ReflectionUtils {

    /**
     * 新建实例
     * @param clazz 类
     * @param <T>   对象
     * @return 实例
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取一个类所有的公共方法
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getPublicMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        return Arrays.stream(methods).filter(o -> Modifier.isPublic(o.getModifiers())).toArray(Method[]::new);
    }

    /**
     * 调用方法
     * @param object 对象
     * @param method 方法
     * @param args   参数
     * @return 返回
     */
    public static Object invoke(Object object, Method method, Object... args) {
        try {
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
