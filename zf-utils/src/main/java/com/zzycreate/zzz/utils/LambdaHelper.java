package com.zzycreate.zzz.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * java8 lambda 工具
 *
 * @author zzycreate
 * @date 2019/6/11
 */
public class LambdaHelper {

    /**
     * 根据key去重，用于 {@link java.util.stream.Stream#filter(Predicate)} 等需要 Predicate 的方法
     *
     * @param keyExtractor Function
     * @param <T>          去重的key的类型
     * @return 去重条件
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>(8);
        // putIfAbsent 如果map中有值，则返回原值，新值也不会放入map中，如果原来没有值，则返回null，本次put的值也会放入map中
        return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
    }

}
