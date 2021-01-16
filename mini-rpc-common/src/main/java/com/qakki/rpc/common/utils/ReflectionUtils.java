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
        return (Method[]) Arrays.stream(methods).filter(o -> Modifier.isPublic(o.getModifiers())).toArray();
    }
}
