package com.zzycreate.zzz.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    /**
     * 将 Collection<E> 转换为 Map<K, List<V>> 结构
     *
     * @param collection 原始数据集合
     * @param keyFunc    key的转换器
     * @param valueFunc  value的转换器
     * @param <E>        转换的集合中的元素类型
     * @param <K>        转换后的Map的key类型
     * @param <V>        转换后的List中的元素类型
     * @return Map
     */
    public static <E, K, V> Map<K, List<V>> groupMapList(Collection<E> collection,
                                                         Function<E, K> keyFunc, Function<E, V> valueFunc) {
        return group(collection, keyFunc, valueFunc, Collectors.toList());
    }

    /**
     * 将 Collection<E> 转换为 Map<K, Set<V>> 结构
     *
     * @param collection 原始数据集合
     * @param keyFunc    key的转换器
     * @param valueFunc  value的转换器
     * @param <E>        转换的集合中的元素类型
     * @param <K>        转换后的Map的key类型
     * @param <V>        转换后的Set中的元素类型
     * @return Map
     */
    public static <E, K, V> Map<K, Set<V>> groupMapSet(Collection<E> collection,
                                                       Function<E, K> keyFunc, Function<E, V> valueFunc) {
        return group(collection, keyFunc, valueFunc, Collectors.toSet());
    }

    /**
     * 分组集合
     *
     * @param collection 原始数据集合
     * @param keyFunc    key的转换器
     * @param valueFunc  value的转换器
     * @param collector  value的集合类型转换器
     * @param <E>        转换的集合中的元素类型
     * @param <K>        转换后的Map的key类型
     * @param <V>        转换后的集合中的元素类型
     * @param <C>        转换后的集合元素类型
     * @return Map
     */
    public static <E, K, V, C> Map<K, C> group(Collection<E> collection,
                                               Function<E, K> keyFunc, Function<E, V> valueFunc,
                                               Collector<V, ?, C> collector) {
        return collection.stream().collect(Collectors.groupingBy(
                keyFunc, Collectors.mapping(valueFunc, collector)
        ));
    }

}
