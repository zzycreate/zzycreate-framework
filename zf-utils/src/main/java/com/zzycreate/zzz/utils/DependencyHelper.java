package com.zzycreate.zzz.utils;

/**
 * 依赖检查公爵
 *
 * @author zzycreate
 * @date 2018/12/1
 */
public class DependencyHelper {

    /**
     * 检查是否存在类名为 clazz 的类
     *
     * @param clazz String, 类名
     * @return boolean, 是否存在类名为 clazz 的类
     */
    public static boolean hasDependency(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 检查是否存在类名为 clazz 的类, 如果不存在则抛出 ClassNotFoundException 异常
     *
     * @param clazz String, 类名
     */
    public static void hasDependencyElseThrow(String clazz) throws ClassNotFoundException {
        Class.forName(clazz);
    }

}
