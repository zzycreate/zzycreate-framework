package com.zzycreate.zzz.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 空值工具类
 * <p>
 * 空（Empty）的定义：
 * 1. 对象为 null 代表空
 * 2. 字符串长度为 0 代表空
 * 3. 集合元素个数为 0 代表空
 * 4. Map 键值对个数为 0 代表空
 * 空白（Blank）的定义：
 * 1. 对象为空(Empty)代表空白(Blank)
 * 2. 字符串长度为 0 或者全是空白字符代表空白
 *
 * @author zzycreate
 * @date 18-12-2
 */
public class EmptyHelper {

    /**
     * 判断是否为空字符串
     *
     * @param str 待检查的字符串
     * @return 是否为空字符串
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断是否不是空字符串
     *
     * @param str 待检查的字符串
     * @return 是否不是空字符串
     */
    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    /**
     * 判断是否为空数组
     *
     * @param arr 待检查的数组
     * @param <E> 数组中元素类型
     * @return 是否为空数组
     */
    public static <E> boolean isEmptyArray(E[] arr) {
        return arr == null || arr.length == 0;
    }

    /**
     * 判断是否不是空数组
     *
     * @param arr 待检查的数组
     * @param <E> 数组中元素类型
     * @return 是否不是空数组
     */
    public static <E> boolean isNotEmptyArray(E[] arr) {
        return !isEmptyArray(arr);
    }

    /**
     * 判断是否为空列表
     *
     * @param list 待检查的列表
     * @param <E>  列表中元素类型
     * @return 是否为空列表
     */
    public static <E> boolean isEmptyList(List<E> list) {
        return isEmptyCollection(list);
    }

    /**
     * 判断是否不是空列表
     *
     * @param list 待检查的列表
     * @param <E>  列表中元素类型
     * @return 是否不是空列表
     */
    public static <E> boolean isNotEmptyList(List<E> list) {
        return !isEmptyList(list);
    }

    /**
     * 判断是否为空 Set
     *
     * @param set 待检查的 Set
     * @param <E> Set 中元素类型
     * @return 是否为空 Set
     */
    public static <E> boolean isEmptySet(Set<E> set) {
        return isEmptyCollection(set);
    }

    /**
     * 判断是否不是空 Set
     *
     * @param set 待检查的 Set
     * @param <E> Set 中元素类型
     * @return 是否不是空 Set
     */
    public static <E> boolean isNotEmptySet(Set<E> set) {
        return !isEmptySet(set);
    }

    /**
     * 判断是否为空集合
     *
     * @param collection 待检查的集合
     * @param <E>        集合中元素类型
     * @return 是否为空集合
     */
    public static <E> boolean isEmptyCollection(Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断是否不是空集合
     *
     * @param collection 待检查的集合
     * @param <E>        集合中元素类型
     * @return 是否不是空集合
     */
    public static <E> boolean isNotEmptyCollection(Collection<E> collection) {
        return !isEmptyCollection(collection);
    }

    /**
     * 判断是否为空 Map
     *
     * @param map 待检查的 Map
     * @param <K> Map 的键类型
     * @param <V> Map 的值类型
     * @return 是否为空 Map
     */
    public static <K, V> boolean isEmptyMap(Map<K, V> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 检查对象是否为空
     * 1. String 类型判断长度
     * 2. Collection 集合类型判断元素数量
     * 3. Map 类型判断键值对数量
     *
     * @param obj 待检查的对象
     * @return 对象是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        // instanceof 关键字用于比较实例是否与类型一致
        if (obj instanceof String) {
            return isEmptyString((String) obj);
        }
        // isArray 用于判断对象是否为数组，会抛出 IllegalArgumentException 异常
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        // isAssignableFrom 用于比较两个Class类型
        if (Collection.class.isAssignableFrom(obj.getClass())) {
            // 如果Object对象是Collection的子集
            return isEmptyCollection((Collection<?>) obj);
        }
        if (Map.class.isAssignableFrom(obj.getClass())) {
            return isEmptyMap((Map<?, ?>) obj);
        }
        return false;
    }

    /**
     * 检查对象是否不是空
     * 结果与 {@code isEmpty(Object obj)} 相反
     *
     * @param obj 待检查的对象
     * @return 对象是否为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断是否为空白(blank)字符串
     * 如果字符串中全是空白字符，则是空白(blank)字符串
     *
     * @param str 待检查的字符串
     * @return 是否为空白字符串
     */
    public static boolean isBlankString(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否不是空白(blank)字符串
     * 如果字符串中全是空白字符，则是空白(blank)字符串
     * 与 {@code isBlankString(String str)} 的结果相反
     *
     * @param str 待检查的字符串
     * @return 是否为空白字符串
     */
    public static boolean isNotBlankString(String str) {
        return !isBlankString(str);
    }

    /**
     * 判断是否为空白(blank)对象
     *
     * @param obj 待检查的对象
     * @return 是否为空白字符串
     */
    public static boolean isBlank(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isBlankString((String) obj);
        }
        return isEmpty(obj);
    }

    /**
     * 判断是否不是空白(blank)对象
     * 与 {@code isBlank(Object obj)} 的结果相反
     *
     * @param obj 待检查的对象
     * @return 是否为空白对象
     */
    public static boolean isNotBlank(Object obj) {
        return !isBlank(obj);
    }

}
