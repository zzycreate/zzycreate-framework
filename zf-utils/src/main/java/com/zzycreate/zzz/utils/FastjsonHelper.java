package com.zzycreate.zzz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Fastjson 工具类
 * 提供一点默认的设置
 * 1. writeNull = true, 值为 null 的键默认不忽略
 * 2. convertNull = false, null 值默认不转换
 * 3. pretty = false, 默认不格式化
 * 4. formatDate = true, 默认日期格式 yyyy-MM-dd
 *
 * @author zzycreate
 * @date 18-12-1
 */
public class FastjsonHelper {

    public static final boolean DEFAULT_WRITE_NULL = true;
    public static final boolean DEFAULT_CONVERT_NULL = false;
    public static final boolean DEFAULT_PRETTY = false;
    public static final boolean DEFAULT_FORMAT_DATE = true;

    /**
     * 将json数据转化为java对象，统一调用此方法
     *
     * @param result json字符串
     * @param clazz  指定类型
     * @return T
     */
    public static <T> T toObject(String result, Class<T> clazz) {
        return toObject(result, clazz, null);
    }

    /**
     * 将json数据转化为java对象，统一调用此方法
     *
     * @param result json字符串
     * @param type   指定类型
     * @return T
     */
    public static <T> T toObject(String result, TypeReference<T> type) {
        return toObject(result, null, type);
    }

    /**
     * 将json数据转化为实体对象
     * 优先使用 CLass 类型指定转换
     *
     * @param result json数据
     * @param clazz  Class类型
     * @param type   TypeReference类型
     * @param <T>    实体类型
     * @return 转化后的实体
     */
    private static <T> T toObject(String result, Class<T> clazz, TypeReference<T> type) {
        if (EmptyHelper.isBlank(result)) {
            return null;
        }
        if (EmptyHelper.isNotEmpty(clazz)) {
            return JSON.parseObject(result, clazz);
        } else if (null != type) {
            return JSON.parseObject(result, type);
        } else {
            throw new IllegalArgumentException("调用jsonToObject方法，没有指定转化类型");
        }
    }

    /**
     * 将java对象转换为json字符串，格式化显示
     *
     * @param obj 待转换对象
     * @return 格式化的json字符串
     */
    public static String format(Object obj) {
        return JSON.toJSONString(obj, initSerializerFeature(DEFAULT_WRITE_NULL, DEFAULT_CONVERT_NULL, true, DEFAULT_FORMAT_DATE));
    }

    /**
     * 将java对象转化为json字符串
     *
     * @param obj 待转化对象信息
     * @return String
     */
    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, initSerializerFeature(DEFAULT_WRITE_NULL, DEFAULT_CONVERT_NULL, DEFAULT_PRETTY, DEFAULT_FORMAT_DATE));
    }

    /**
     * 将java对象转化为json字符串
     *
     * @param obj       待转化对象信息
     * @param writeNull 默认true; 是否忽略值为null的字段
     * @return String
     */
    public static String toJsonString(Object obj, boolean writeNull) {
        return JSON.toJSONString(obj, initSerializerFeature(writeNull, DEFAULT_CONVERT_NULL, DEFAULT_PRETTY, DEFAULT_FORMAT_DATE));
    }

    /**
     * 将java对象转化为json字符串
     *
     * @param obj        待转化对象信息
     * @param writeNull  默认true; 是否忽略值为null的字段
     * @param formatDate 默认true;是否格式化时间
     * @return String
     */
    public static String toJsonString(Object obj, boolean writeNull, boolean formatDate) {
        return JSON.toJSONString(obj, initSerializerFeature(writeNull, DEFAULT_CONVERT_NULL, DEFAULT_PRETTY, formatDate));
    }


    /**
     * 将java对象转化为json字符串，并根据filter来过滤掉不需要返回的字段或者需要返回的字段，默认会格式化时间
     *
     * @param obj      待转化对象信息
     * @param excludes 不需要返回的字段
     * @return String
     */
    public static String toJsonString(Object obj, Set<String> excludes) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (EmptyHelper.isNotEmpty(excludes)) {
            filter.getExcludes().addAll(excludes);
        }
        return JSON.toJSONString(obj, filter, initSerializerFeature(DEFAULT_WRITE_NULL, DEFAULT_CONVERT_NULL, DEFAULT_PRETTY, true));
    }

    /**
     * 将java对象转化为json字符串，并根据filter来过滤掉不需要返回的字段或者需要返回的字段
     *
     * @param obj         待转化对象信息
     * @param excludes    不需要返回的字段，优先级高
     * @param includes    需要返回的字段
     * @param writeNull   默认true; 是否忽略值为null的字段
     * @param convertNull 默认false; null 的字段转换成对应的格式的默认值，只有在允许输出 null 值字段才会有效果，否则字段都不存在
     * @param pretty      默认false;是否格式化json字符串
     * @param formatDate  默认true;是否格式化时间
     * @return String
     */
    public static String toJsonString(Object obj, Set<String> excludes, Set<String> includes,
                                      boolean writeNull, boolean convertNull, boolean pretty, boolean formatDate) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (EmptyHelper.isNotEmpty(includes)) {
            filter.getIncludes().addAll(includes);
        }
        if (EmptyHelper.isNotEmpty(excludes)) {
            filter.getExcludes().addAll(excludes);
        }
        return JSON.toJSONString(obj, filter, initSerializerFeature(writeNull, convertNull, pretty, formatDate));
    }

    /**
     * 初始化initSerializerFeature，类型说明：
     * 1.{@link com.alibaba.fastjson.serializer.SerializerFeature#WriteMapNullValue}： 是否输出值为null的字段,默认为false
     * 2.{@link com.alibaba.fastjson.serializer.SerializerFeature#WriteNullListAsEmpty}： List字段如果为null,输出为[],而非null
     * {@link com.alibaba.fastjson.serializer.SerializerFeature#WriteNullStringAsEmpty}： 字符类型字段如果为null,输出为”“,而非null
     * {@link com.alibaba.fastjson.serializer.SerializerFeature#WriteNullNumberAsZero}： 数值字段如果为null,输出为0,而非null
     * {@link com.alibaba.fastjson.serializer.SerializerFeature#WriteNullBooleanAsFalse}： Boolean字段如果为null,输出为false,而非null
     * 3.{@link com.alibaba.fastjson.serializer.SerializerFeature#PrettyFormat} 格式化输出， 默认为true
     * 4.{@link com.alibaba.fastjson.serializer.SerializerFeature#WriteDateUseDateFormat}: 全局修改日期格式,默认为false。
     * JSON.DEFAULT_DATE_FORMAT = “yyyy-MM-dd”;
     * JSON.toJsonString(obj, SerializerFeature.WriteDateUseDateFormat);
     * 5.{@link com.alibaba.fastjson.serializer.SerializerFeature#DisableCircularReferenceDetect}: 消除对同一对象循环引用的问题，默认为false
     *
     * @param writeNull   是否允许有 null 的字段
     * @param convertNull null 的字段转换成对应的格式的默认值，只有在允许输出 null 值字段才会有效果，否则字段都不存在
     * @param pretty      格式化, 格式化后打印会更好看，但是占用空间更多
     * @param formatDate  是否格式化日期为 yyyy-MM-dd
     * @return SerializerFeature[]
     */
    private static SerializerFeature[] initSerializerFeature(boolean writeNull, boolean convertNull, boolean pretty, boolean formatDate) {
        List<SerializerFeature> serializerFeatures = new ArrayList<>(3);
        if (writeNull) {
            //允许 null 值,  "key": null
            serializerFeatures.add(SerializerFeature.WriteMapNullValue);
        }
        if (convertNull) {
            // 转换 null， (T[])null -> [], (String)null -> "", (Integer)null -> 0, (Boolean)null -> false
            serializerFeatures.add(SerializerFeature.WriteNullListAsEmpty);
            serializerFeatures.add(SerializerFeature.WriteNullStringAsEmpty);
            serializerFeatures.add(SerializerFeature.WriteNullNumberAsZero);
            serializerFeatures.add(SerializerFeature.WriteNullBooleanAsFalse);
        }
        if (formatDate) {
            serializerFeatures.add(SerializerFeature.WriteDateUseDateFormat);
        }
        if (pretty) {
            // 允许格式化
            serializerFeatures.add(SerializerFeature.PrettyFormat);
        }
        serializerFeatures.add(SerializerFeature.DisableCircularReferenceDetect);
        return CastHelper.toArray(serializerFeatures, SerializerFeature.class);
    }


}
