package com.zzycreate.zzz.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.*;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * 类型转换工具类
 * 代码参考：https://github.com/venusdrogon/feilong-core/blob/master/src/main/java/com/feilong/core/bean/ConvertUtil.java
 * 数组转型主要利用 {@link org.apache.commons.lang3.ArrayUtils}
 * 对象与集合转换主要利用 {@link org.apache.commons.beanutils.ConvertUtils}
 *
 * @author zzycreate
 * @date 18-12-3
 */
@Slf4j
public class CastHelper {

    /**
     * 单例私有构造器
     */
    private CastHelper() {
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 如果 Class 对象是基本类型，转换为包装类型
     *
     * @param clazz 待转换的 Class
     * @return 包装后的类型 Class
     */
    public static <T> Class<?> wrapClass(Class<T> clazz) {
        if (clazz != null && clazz.isPrimitive()) {
            switch (clazz.getSimpleName()) {
                case "char":
                    return Character.class;
                case "byte":
                    return Byte.class;
                case "int":
                    return Integer.class;
                case "short":
                    return Short.class;
                case "long":
                    return Long.class;
                case "float":
                    return Float.class;
                case "double":
                    return Double.class;
                case "boolean":
                    return Boolean.class;
                default:
            }
        }
        return clazz;
    }

    /**
     * 如果 Class 对象是基本类型，转换为包装类型
     *
     * @param data 检查对象
     * @return 包装后的类型 Class
     */
    public static <T> Class<?> wrap(T data) {
        if (data == null) {
            return null;
        }
        if(data instanceof Class){
            Class clazz = (Class)data;
            return CastHelper.wrapClass(clazz);
        }
        Class<?> clazz = data.getClass();
        return CastHelper.wrapClass(clazz);
    }

    /**
     * 检查对象 obj 是否是指定 Class 类型的实例
     *
     * @param obj   待检查的对象
     * @param clazz 待检查的 Class
     * @param <T>   Class 的类型
     * @return obj是否为 Class 类型的实例
     */
    public static <T> boolean is(Object obj, Class<T> clazz) {
        if (clazz == null) {
            return false;
        }
        log.debug("obj type is {}, clazz type is {}", obj.getClass(), clazz);
        // Class 的 isInstance 方法动态等价于 instanceof 关键字, 但是 isInstance 判断基本数据类型的时候会有问题， 因此使用 isAssignableFrom 进行判断;
        // isAssignableFrom 是判断调用对象是否是参数对象的父类或者超类，并使用 wrap 方法对基本数据类型进行包装判断。
        return CastHelper.wrapClass(clazz).isAssignableFrom(obj.getClass());
    }

    /**
     * 检查对象 obj 是否是数组
     *
     * @param obj 待检查的对象
     * @return 检查 obj 是否为数组
     */
    public static boolean isArray(Object obj) {
        // isArray 方法是 native 方法
        return obj != null && obj.getClass().isArray();
    }

    /**
     * 检查对象 obj 是否是集合
     *
     * @param obj 待检查的对象
     * @return 检查对象 obj 是否是集合
     */
    public static boolean isCollection(Object obj) {
        return is(obj, Collection.class);
    }

    //---------------------------------------------------------------

    static {
        //初始化注册器.
        CastHelper.registerStandardDefaultNull();
    }

    /**
     * Register standard default null.
     * 注册默认转换规则
     */
    private static void registerStandardDefaultNull() {
        ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
        ConvertUtils.register(new BigIntegerConverter(null), BigInteger.class);
        ConvertUtils.register(new BooleanConverter(null), Boolean.class);
        ConvertUtils.register(new ByteConverter(null), Byte.class);
        ConvertUtils.register(new CharacterConverter(null), Character.class);
        ConvertUtils.register(new DoubleConverter(null), Double.class);
        ConvertUtils.register(new FloatConverter(null), Float.class);
        ConvertUtils.register(new IntegerConverter(null), Integer.class);
        ConvertUtils.register(new LongConverter(null), Long.class);
        ConvertUtils.register(new ShortConverter(null), Short.class);
        ConvertUtils.register(new StringConverter(null), String.class);
    }

    //---------------------toBoolean------------------------------------------

    /**
     * 将对象转换成 {@link Boolean}类型.
     *
     * <h3>示例:</h3>
     * <blockquote>
     * <pre class="code">
     * CastHelper.toBoolean(null)      =   null
     * CastHelper.toBoolean(1L)        =   true
     * CastHelper.toBoolean("1")       =   true
     * CastHelper.toBoolean("9")       =   null
     * CastHelper.toBoolean("1,2,3")   =   null
     * </pre>
     * </blockquote>
     *
     * <h3>逻辑及规则:</h3>
     * <blockquote>
     * <ul>
     * <li>如果 "true", "yes", "y", "on", "1" <span style="color:green">(忽视大小写)</span>, 返回 true</li>
     * <li>如果 "false", "no", "n", "off", "0" <span style="color:green">(忽视大小写)</span>, 返回 false</li>
     * <li>其他抛出 {@link org.apache.commons.beanutils.ConversionException} 异常
     * </ul>
     * </blockquote>
     *
     * <h3>和 {@link Boolean#parseBoolean(String)}的区别:</h3>
     * <blockquote>
     * <p>
     * {@link Boolean#parseBoolean(String)},仅当 <code>(String != null) 并且 String.equalsIgnoreCase("true")</code> 返回 true
     * </p>
     * </blockquote>
     *
     * @param obj 待转换的对象
     * @return 转换后的 Boolean 结果
     */
    public static Boolean toBoolean(Object obj) {
        return new BooleanConverter(null).convert(Boolean.class, obj);
    }

    /**
     * 将对象转换成 {@link Boolean}类型(支持自定义转换规则).
     * <p>
     * 本质是调用 {@link org.apache.commons.beanutils.converters.BooleanConverter#BooleanConverter(String[], String[], Object)}
     * 设置 trueStrings 和 falseStrings
     * </p>
     *
     * @param obj       待转换的对象
     * @param converter 自定义转换器
     * @return 转换后的 Boolean 结果
     */
    public static Boolean toBoolean(Object obj, BooleanConverter converter) {
        if (converter == null) {
            return new BooleanConverter(null).convert(Boolean.class, obj);
        }
        return converter.convert(Boolean.class, obj);
    }

    //----------------------toInteger-----------------------------------------

    /**
     * 将对象转换成 {@link Integer}类型.
     *
     * <h3>示例:</h3>
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.toInteger(null)                            = null
     * CastHelper.toInteger("aaaa")                          = null
     * CastHelper.toInteger(8L)                              = 8
     * CastHelper.toInteger("8")                             = 8
     * CastHelper.toInteger(new BigDecimal("8"))             = 8
     * CastHelper.toInteger(new String[] { "1", "2", "3" }) = 1
     * CastHelper.toInteger(Casthelper.toList("1", "2"))    = 1
     * </pre>
     *
     * <p>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     * <p>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     *
     * </blockquote>
     *
     * <p>
     * 该方法非常适用 获取request请求的分页参数
     * </p>
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <p>
     * 原来的写法:
     * </p>
     *
     * <pre class="code">
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     try{
     *         int pageNo = Integer.parseInt(pageNoString);
     *         return pageNo;
     *     }catch (Exception e){
     *         LOGGER.error(e.getClass().getName(), e);
     *     }
     *     return 1; <span style="color:green">// 不带这个参数或者转换异常返回1</span>
     * }
     * </pre>
     *
     * <p>
     * 现在可以更改成:
     * </p>
     *
     * <pre class="code">
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     Integer pageNo = CastHelper.toInteger(pageNoString);
     *     return null == pageNo ? 1 : pageNo;
     * }
     * </pre>
     *
     * <p>
     * 当然对于这种场景,最快捷的:调用支持默认值的 {@link #toInteger(Object, Integer)} 方法
     * </p>
     *
     * <pre class="code">
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     return CastHelper.toInteger(pageNoString, 1);
     * }
     * </pre>
     *
     * </blockquote>
     *
     * @param obj 待转换的值
     * @return 如果对象是null, 返回 null<br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 null
     */
    public static Integer toInteger(Object obj) {
        return toInteger(obj, null);
    }

    /**
     * 将对象转换成 {@link Integer}类型,如果转换不了返回默认值 <code>defaultValue</code>.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.toInteger(null,null)                         = null
     * CastHelper.toInteger(null,1)                            = 1
     * CastHelper.toInteger("aaaa",1)                          = 1
     * CastHelper.toInteger(8L,1)                              = 8
     * CastHelper.toInteger("8",1)                             = 8
     * CastHelper.toInteger(new BigDecimal("8"),1)             = 8
     * CastHelper.toInteger(new String[] { "1", "2", "3" }, 8) = 1
     * CastHelper.toInteger(toList("1", "2"), 8)               = 1
     * </pre>
     *
     * <p>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     * <p>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     *
     * </blockquote>
     *
     * <p>
     * 该方法非常适用 获取request请求的分页参数
     * </p>
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     * <p>
     * 原来的写法:
     *
     * <pre class="code">
     *
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     try{
     *         int pageNo = Integer.parseInt(pageNoString);
     *         return pageNo;
     *     }catch (Exception e){
     *         LOGGER.error(e.getClass().getName(), e);
     *     }
     *     return 1; <span style="color:green">// 不带这个参数或者转换异常返回1</span>
     * }
     *
     * </pre>
     * <p>
     * 现在可以更改成:
     *
     * <pre class="code">
     *
     * public static Integer getCurrentPageNo(HttpServletRequest request,String pageParamName){
     *     String pageNoString = RequestUtil.getParameter(request, pageParamName);
     *     return CastHelper.toInteger(pageNoString, 1);
     * }
     * </pre>
     *
     * </blockquote>
     *
     * @param obj          待转换的值
     * @param defaultValue 默认值
     * @return 如果对象是null, 返回 <code>defaultValue</code> <br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 <code>defaultValue</code>
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        return new IntegerConverter(defaultValue).convert(Integer.class, obj);
    }

    //------------------------toLong---------------------------------------

    /**
     * 将对象转换成 {@link Long}类型.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.toLong(null)                           = null
     * CastHelper.toLong("aaaa")                         = null
     * CastHelper.toLong(8)                              = 8L
     * CastHelper.toLong("8")                            = 8L
     * CastHelper.toLong(new BigDecimal("8"))            = 8L
     * CastHelper.toLong(new String[] { "1", "2", "3" }) = 1L
     * CastHelper.toLong(toList("1", "2"))               = 1L
     * </pre>
     *
     * <p>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     * <p>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     *
     * </blockquote>
     *
     * @param obj 包含数字的对象.
     * @return 如果对象是null, 返回 null<br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 null
     */
    public static Long toLong(Object obj) {
        return toLong(obj, null);
    }

    /**
     * 将对象转换成 {@link Long}类型.
     *
     * @param obj          待转换的值
     * @param defaultValue 默认值
     * @return 如果对象是null, 返回 null<br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 null
     */
    public static Long toLong(Object obj, Long defaultValue) {
        return new LongConverter(defaultValue).convert(Long.class, obj);
    }


    //------------------------toBigDecimal---------------------------------------

    /**
     * 将对象转换成 {@link BigDecimal}.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.toBigDecimal(null)                     = null
     * CastHelper.toBigDecimal("aaaa")                   = null
     * CastHelper.toBigDecimal(8)                        = BigDecimal.valueOf(8)
     * CastHelper.toBigDecimal("8")                      = BigDecimal.valueOf(8)
     * CastHelper.toBigDecimal(new BigDecimal("8"))      = BigDecimal.valueOf(8)
     * </pre>
     *
     * <p>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     *
     * <pre class="code">
     * CastHelper.toBigDecimal(new String[] { "1", "2", "3" }) = BigDecimal.valueOf(1)
     * </pre>
     *
     * <p>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换
     * </p>
     *
     * <pre class="code">
     * CastHelper.toBigDecimal(toList("1", "2")) = BigDecimal.valueOf(1)
     * </pre>
     *
     * </blockquote>
     *
     * <h3>{@link Double} 转成 {@link BigDecimal}注意点:</h3>
     *
     * <blockquote>
     *
     * <p>
     * <span style="color:red">推荐使用 {@link BigDecimal#valueOf(double)}</span>,
     * 不建议使用 <code>new BigDecimal(double)</code>,参见 JDK API<br>
     * </p>
     *
     * <ul>
     * <li>{@code new BigDecimal(0.1) ====> 0.1000000000000000055511151231257827021181583404541015625}</li>
     * <li>{@code BigDecimal.valueOf(0.1) ====> 0.1}</li>
     * </ul>
     *
     * </blockquote>
     *
     * @param obj 待转换的值
     * @return 如果对象是null, 返回 null<br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 null
     */
    public static BigDecimal toBigDecimal(Object obj) {
        return toBigDecimal(obj, null);
    }


    /**
     * 将对象转换成 {@link BigDecimal}.可设置默认值
     *
     * @param obj          待转换的值
     * @param defaultValue 默认值
     * @return 如果对象是null, 返回 null<br>
     * 如果传入的参数对象是 <b>数组</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果传入的参数对象是 <b>集合</b>,那么<b>取第一个元素</b>进行转换<br>
     * 如果找不到转换器或者转换的时候出现了异常,返回 null
     */
    public static BigDecimal toBigDecimal(Object obj, BigDecimal defaultValue) {
        return new BigDecimalConverter(defaultValue).convert(BigDecimal.class, obj);
    }

    //------------------------ 数组 ---------------------------------------

    /**
     * 将对象转成{@link Integer} 数组.
     *
     * <h3>说明:</h3>
     * <blockquote>
     *
     * <dl>
     * <dt>如果参数 对象是 <code>数组</code> 或者 {@link Collection}，
     * 会构造一个<code>Integer</code>数组,长度就是 对象的大小或者长度,然后迭代对象依次逐个进行转换</dt>
     *
     * <dd>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toIntegers(new String[] { "1", "2", "3" })       = [1,2,3]
     * CastHelper.toIntegers(toList("1", "2", "3"))    = [1,2,3]
     * </pre>
     *
     * </dd>
     *
     * <dt>如果参数 对象不是 <code>数组</code> 也不是 {@link Collection} , 那么首先会将待转对象转成集合</dt>
     *
     * <dd>
     *
     * <ol>
     * <li>如果待转对象是{@link Number}, {@link Boolean} 或者 {@link Date} ,那么构造只有一个对象元素的 List返回.</li>
     * <li>其他类型将转成字符串,然后转成list.</li>
     *
     * <p>
     * 具体转换逻辑为:
     * </p>
     *
     * <ul>
     * <li>字符串期望是一个逗号分隔的字符串.</li>
     * <li>字符串可以被'{' 开头 和 '}'结尾的分隔符包裹,程序内部会自动截取.</li>
     * <li>会去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     *
     * </li>
     * </ol>
     *
     * <p>
     * 得到list之后,会构造一个<code>Integer</code>数组,长度就是 对象的大小或者长度,然后迭代对象依次逐个进行转换
     * </p>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toIntegers("1,2,3")                  = new Integer[] { 1, 2, 3 }
     * CastHelper.toIntegers("{1,2,3}")                = new Integer[] { 1, 2, 3 }
     * CastHelper.toIntegers("{ 1 ,2,3}")              = new Integer[] { 1, 2, 3 }
     * CastHelper.toIntegers("1,2, 3")                 = new Integer[] { 1, 2, 3 }
     * CastHelper.toIntegers("1,2 , 3")                = new Integer[] { 1, 2, 3 }
     * </pre>
     *
     * </dd>
     *
     * </dl>
     *
     * <p>
     * 每个元素转换成 Integer的时候,会转换类型,具体的规则是:
     * </p>
     *
     * <blockquote>
     *
     * <dl>
     * <dt>1.如果 元素是 Number类型</dt>
     * <dd>那么会转换为数字</dd>
     *
     * <dt>2.如果 元素是 Boolean类型</dt>
     * <dd>那么 true被转成1,false 转成 0</dd>
     *
     * <dt>3.其他情况</dt>
     * <dd>将元素转成字符串,并trim,再进行转换</dd>
     *
     * <dt>4.元素中有null的情况</dt>
     * <dd>如果有元素是null, 没有默认值的话,会抛出异常,然后catch之后返回 empty Integer 数组 </dd>
     * </dl>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toIntegers(toList("1", "2", <span style="color:red">" 3"</span>))        = new Integer[] { 1, 2, 3 }
     * CastHelper.toIntegers(toArray(true, false, false))                                  = new Integer[] { 1, 0, 0 }
     * CastHelper.toIntegers(new String[] { "1", null, "2", "3" })                         = new Integer[] {}
     * </pre>
     *
     * </blockquote>
     *
     * </blockquote>
     *
     * @param obj 需要被转换的值
     * @return 如果对象是null, 返回 null<br>
     */
    public static Integer[] toIntegerArray(Object obj) {
        return convert(obj, Integer[].class);
    }

    /**
     * 将对象转成 Long 数组.
     *
     * <h3>说明:</h3>
     * <blockquote>
     *
     * <dl>
     * <dt>如果待转对象参数是 <code>数组</code> 或者 {@link Collection},
     * 会构造一个<code>Long</code>数组,长度就是 对象的大小或者长度,然后迭代对象依次逐个进行转换</dt>
     *
     * <dd>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toLongArray(new String[] { "1", "2", "3" }       = [1L,2L,3L]
     * CastHelper.toLongArray(toList("1", "2", "3"))               = [1L,2L,3L]
     * </pre>
     *
     * </dd>
     *
     * <dt>如果待转对象不是 <code>数组</code> 也不是 {@link Collection} ，那么首先会将待转对象转成集合</dt>
     *
     * <dd>
     *
     * <ol>
     * <li>如果 对象是{@link Number}, {@link Boolean} 或者 {@link Date} ,那么构造只有一个 对象元素的 List返回.</li>
     * <li>其他类型将转成字符串,然后转成list.
     *
     * <p>
     * 具体转换逻辑为:
     * </p>
     *
     * <ul>
     * <li>字符串期望是一个逗号分隔的字符串.</li>
     * <li>字符串可以被'{' 开头 和 '}'结尾的分隔符包裹,程序内部会自动截取.</li>
     * <li>会去除前后空白.</li>
     * <li>Elements in the list may be delimited by single or double quotes. Within a quoted elements, the normal Java escape sequences are
     * valid.</li>
     * </ul>
     *
     * </li>
     * </ol>
     *
     * <p>
     * 得到list之后,会构造一个 <code>Long</code> 数组,长度就是 对象的大小或者长度,然后迭代对象依次逐个进行转换
     * </p>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toLongArray("1,2,3")                  = new Long[] { 1L, 2L, 3L }
     * CastHelper.toLongArray("{1,2,3}")                = new Long[] { 1L, 2L, 3L }
     * CastHelper.toLongArray("{ 1 ,2,3}")              = new Long[] { 1L, 2L, 3L }
     * CastHelper.toLongArray("1,2, 3")                 = new Long[] { 1L, 2L, 3L }
     * CastHelper.toLongArray("1,2 , 3")                = new Long[] { 1L, 2L, 3L }
     * </pre>
     *
     * </dd>
     *
     * </dl>
     *
     * <p>
     * 每个元素转换成 Integer的时候, 具体的转换规则是:
     * </p>
     *
     * <blockquote>
     *
     * <dl>
     * <dt>1.如果 元素是 Number类型</dt>
     * <dd>那么会转换为数字</dd>
     *
     * <dt>2.如果 元素是 Boolean类型</dt>
     * <dd>那么 true被转成1L,false 转成 0L</dd>
     *
     * <dt>3.其他情况</dt>
     * <dd>将元素转成字符串,并trim,再进行转换</dd>
     *
     * <dt>4.元素是null的情况</dt>
     * <dd>如果有元素是null, 没有默认值的话,会抛出异常,然后catch之后返回 empty Long 数组 </dd>
     * </dl>
     *
     * <h4>示例:</h4>
     *
     * <pre class="code">
     * CastHelper.toLongArray(toList("1", "2", <span style="color:red">" 3"</span>))        = new Long[] { 1L, 2L, 3L }
     * CastHelper.toLongArray(toArray(true, false, false))                                  = new Long[] { 1L, 0L, 0L }
     * CastHelper.toLongArray(new String[] { "1", null, "2", "3" })                         = new Long[] {}
     * </pre>
     *
     * </blockquote>
     *
     * </blockquote>
     *
     * <h3>特别适合以下形式的代码:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     *
     * protected long[] getOrderIdLongs(String orderIds){
     *     <span style="color:green">// 确认交易时候插入数据库的时候,不应该会出现空的情况</span>
     *     String[] orderIdArray = orderIds.split(",");
     *     int orderLength = orderIdArray.length;
     *     long[] ids = new long[orderLength];
     *     for (int i = 0, j = orderLength; i {@code <} j; ++i){
     *         ids[i] = Long.parseLong(orderIdArray[i]);
     *     }
     *     return ids;
     * }
     *
     * </pre>
     *
     * <b>可以重构成:</b>
     *
     * <pre class="code">
     *
     * protected long[] getOrderIdLongs(String orderIds){
     *     return toLongArray(orderIds);
     * }
     * </pre>
     *
     * </blockquote>
     *
     * @param toBeConvertedValue the to be converted value
     * @return 如果对象是null, 返回 null<br>
     */
    public static Long[] toLongArray(Object toBeConvertedValue) {
        return convert(toBeConvertedValue, Long[].class);
    }

    //--------------------- toEnumeration ------------------------------------------

    /**
     * 将集合 <code>collection</code> 转成<code>Enumeration</code>.
     *
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>一般情况,你可能不需要这个方法,不过在一些API的时候,需要<code>Enumeration</code>参数,此时调用这个方法来进行转换会比较方便</li>
     * </ol>
     * </blockquote>
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.toEnumeration(null) = Collections.emptyEnumeration()
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>        the generic type
     * @param collection 集合
     * @return 如果 <code>collection</code> 是null,返回 {@link Collections#emptyEnumeration()}<br>
     * 否则返回{@link Collections#enumeration(Collection)}
     */
    public static <T> Enumeration<T> toEnumeration(final Collection<T> collection) {
        return null == collection ? Collections.emptyEnumeration() : Collections.enumeration(collection);
    }

    //-------------------------- toList -------------------------------------

    /**
     * 将枚举 <code>enumeration</code> 转成 {@link List}.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * toList((Enumeration{@code <String>}) null) = emptyList()
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>         the generic type
     * @param enumeration the enumeration
     * @return 如果 <code>enumeration</code> 是null,返回 {@link Collections#emptyList()}<br>
     * 否则返回 {@link Collections#list(Enumeration)}
     */
    public static <T> List<T> toList(final Enumeration<T> enumeration) {
        return null == enumeration ? Collections.emptyList() : Collections.list(enumeration);
    }

    /**
     * 将 集合 <code>collection</code> 转成 list.
     *
     * <h3>说明:</h3>
     * <blockquote>
     * <ol>
     * <li>此方法很适合快速的将set转成list这样的操作</li>
     * </ol>
     * </blockquote>
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * Set{@code <String>} set = new LinkedHashSet{@code <>}();
     * Collections.addAll(set, "a", "a", "b", "b");
     * LOGGER.debug("{}", toList(set));
     * </pre>
     *
     * <b>返回:</b>
     *
     * <pre class="code">
     * [a,b]
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @return 如果 <code>collection</code> 是null,返回 {@link Collections#emptyList()}<br>
     * 如果 <code>collection instanceof List</code>,那么强转成 list返回<br>
     * 否则返回 <code>new ArrayList(collection)</code>
     */
    public static <T> List<T> toList(final Collection<T> collection) {
        if (null == collection) {
            return Collections.emptyList();
        }
        return collection instanceof List ? (List<T>) collection : new ArrayList<>(collection);
    }

    /**
     * 数组转成 ({@link ArrayList ArrayList}).
     *
     * @param <T>    the generic type
     * @param arrays T数组
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptyList()}<br>
     * 否则返回 {@code new ArrayList<T>(Arrays.asList(arrays));}
     */
    public static <T> List<T> toList(final T... arrays) {
        return EmptyHelper.isEmpty(arrays) ? Collections.emptyList() : new ArrayList<>(Arrays.asList(arrays));
    }

    //---------------------------toSet------------------------------------

    /**
     * 数组转成 Set ({@link LinkedHashSet}).
     *
     * @param <T>        the generic type
     * @param collection the collection
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptySet()}<br>
     * 否则返回 {@code new LinkedHashSet<T>(Arrays.asList(arrays));}
     */
    public static <T> Set<T> toSet(final Collection<T> collection) {
        if (null == collection) {
            return Collections.emptySet();
        }
        return collection instanceof Set ? (Set<T>) collection : new LinkedHashSet<>(collection);
    }

    /**
     * 数组转成 Set ({@link LinkedHashSet}).
     *
     * @param <T>    the generic type
     * @param arrays the arrays
     * @return 如果 <code>arrays</code> 是null或者empty,返回 {@link Collections#emptySet()}<br>
     * 否则返回 {@code new LinkedHashSet<T>(Arrays.asList(arrays));}
     */
    public static <T> Set<T> toSet(final T... arrays) {
        return EmptyHelper.isEmpty(arrays) ? Collections.emptySet() : new LinkedHashSet<>(Arrays.asList(arrays));
    }

    //----------------------------toArray-----------------------------------

    /**
     * 将动态数组转成数组.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * String[] array = ConvertUtil.toArray("1", "2");                  =   ["1", "2"];
     *
     * String[] emptyArray = CastHelper.{@code <String>}toArray();     =   [] ; //= new String[] {};
     * Integer[] emptyArray = CastHelper.{@code <Integer>}toArray();   =   [] ; //= new Integer[] {};
     *
     * <span style="color:red">//注意</span>
     * String[] nullArray = CastHelper.toArray(null)                   =   null;
     * CastHelper.toArray((String) null)                               =   new String[] { null }
     * </pre>
     *
     * </blockquote>
     *
     * <h3>注意:</h3>
     *
     * <blockquote>
     * <p>
     * 数组是具体化的(reified),而泛型在运行时是被擦除的(erasure)。<br>
     * 数组是在运行时才去判断数组元素的类型约束,而泛型正好相反,在运行时,泛型的类型信息是会被擦除的,只有编译的时候才会对类型进行强化。
     * </p>
     *
     * <b>泛型擦除的规则:</b>
     *
     * <ol>
     * <li>所有参数化容器类都被擦除成非参数化的(raw type); 如 List{@code <E>}、List{@code <List<E>>}都被擦除成 List</li>
     * <li>所有参数化数组都被擦除成非参数化的数组;如 List{@code <E>}[],被擦除成 List[]</li>
     * <li>Raw type 的容器类,被擦除成其自身,如 List{@code <E>}被擦 除成 List</li>
     * <li>原生类型(int,String 还有 wrapper 类)都擦除成他们的自身</li>
     * <li>参数类型 E,如果没有上限,则被擦除成 Object</li>
     * <li>所有约束参数如{@code <? Extends E>}、{@code <X extends E>}都被擦 除成 E</li>
     * <li>如果有多个约束,擦除成第一个,如{@code <T extends Object & E>},则擦除成 Object</li>
     * </ol>
     *
     * <p>
     * 这将会导致下面的代码:
     * </p>
     *
     * <pre class="code">
     *
     * public static {@code <K, V>} Map{@code <K, V[]>} toArrayValueMap(Map{@code <K, V>} singleValueMap){
     *     Map{@code <K, V[]>} arrayValueMap = newLinkedHashMap(singleValueMap.size());//保证顺序和参数singleValueMap顺序相同
     *     for (Map.Entry{@code <K, V>} entry : singleValueMap.entrySet()){
     *         arrayValueMap.put(entry.getKey(), toArray(entry.getValue()));//注意此处的Value不要声明成V,否则会变成Object数组
     *     }
     *     return arrayValueMap;
     * }
     * </pre>
     * <p>
     * 调用的时候,
     *
     * <pre class="code">
     * Map{@code <String, String>} singleValueMap = MapUtil.newLinkedHashMap(2);
     * singleValueMap.put("province", "江苏省");
     * singleValueMap.put("city", "南通市");
     *
     * Map{@code <String, String[]>} arrayValueMap = MapUtil.toArrayValueMap(singleValueMap);
     * String[] strings = arrayValueMap.get("province");//此时返回的是 Object[]
     * </pre>
     * <p>
     * 会出现异常
     *
     * <pre class="code">
     * java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Ljava.lang.String;
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>    the generic type
     * @param arrays the arrays
     * @return 如果 <code>arrays</code> 是null,返回null<br>
     */
    public static <T> T[] toArray(T... arrays) {
        return ArrayUtils.toArray(arrays);
    }

    /**
     * 将集合 <code>collection</code> 转成数组.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * List{@code <String>} list = new ArrayList{@code <>}();
     * list.add("xinge");
     * list.add("feilong");
     * </pre>
     * <p>
     * 以前你需要写成:
     *
     * <pre class="code">
     * list.toArray(new String[list.size()]);
     * </pre>
     * <p>
     * 现在你只需要写成:
     *
     * <pre class="code">
     * String[] array = CastHelper.toArray(list, String.class);
     * LOGGER.info(JsonUtil.format(array));
     * </pre>
     *
     * <b>返回:</b>
     *
     * <pre class="code">
     * ["xinge","feilong"]
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>                the generic type
     * @param collection         collection
     * @param arrayComponentType 数组组件类型的 Class
     * @return 如果 <code>collection</code> 是null,直接返回null<br>
     * 如果 <code>arrayComponentType</code> 是null,抛出 {@link NullPointerException}<br>
     */
    public static <T> T[] toArray(Collection<T> collection, Class<T> arrayComponentType) {
        if (null == collection) {
            return null;
        }
        if (arrayComponentType == null) {
            throw new IllegalArgumentException("arrayComponentType must not be null");
        }

        T[] array = (T[]) Array.newInstance(arrayComponentType, collection.size());

        //注意,toArray(new Object[0]) 和 toArray() 在功能上是相同的.
        return collection.toArray(array);
    }

    /**
     * 将字符串数组 <code>toBeConvertedValue</code> 转成指定类型 <code>targetType</code> 的数组.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * String[] ss = { "2", "1" };
     * toArray(ss, Long.class);                                     =   new Long[] { 2L, 1L }
     *
     * CastHelper.toArray((String[]) null, Serializable.class)     =   null
     * </pre>
     *
     * </blockquote>
     *
     * @param <T>        the generic type
     * @param obj        the values
     * @param targetType 要被转换的目标类型
     * @return 如果 <code>toBeConvertedValue</code> 是null,那么返回null<br>
     * 如果 <code>targetType</code> 是null,抛出 {@link NullPointerException}<br>
     * 否则调用 {@link ConvertUtils#convert(String[], Class)}
     */
    public static <T> T[] toArray(String[] obj, Class<T> targetType) {
        //如果指定的类型 本身就是数组类型的class,那么返回的类型就是该数组类型,否则将基于指定类型构造数组.
        return null == obj ? null : (T[]) ConvertUtils.convert(obj, targetType);
    }

    /**
     * 将对象转成指定 <code>targetType</code> 类型的对象.
     *
     * <h3>示例:</h3>
     *
     * <blockquote>
     *
     * <pre class="code">
     * CastHelper.convert("1", Integer.class)      =1
     * CastHelper.convert("", Integer.class)       =0
     * CastHelper.convert("1", Long.class)         =1
     * </pre>
     *
     * </blockquote>
     *
     * <h3>此外,该方法特别适合数组类型的转换,比如 Type[] 转成 Class []:</h3>
     *
     * <blockquote>
     * <p>
     * 原来的写法:
     *
     * <pre class="code">
     *
     * Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
     * int length = actualTypeArguments.length;
     * Class{@code <?>}[] klasses = new Class{@code <?>}[length];
     * for (int i = 0, j = length; i {@code <} j; ++i){
     *     klasses[i] = (Class{@code <?>}) actualTypeArguments[i];
     * }
     *
     * return klasses;
     *
     * </pre>
     * <p>
     * 现在可以重构成:
     *
     * <pre class="code">
     * Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
     * return convert(actualTypeArguments, Class[].class);
     * </pre>
     *
     * </blockquote>
     *
     * <h3>注意:</h3>
     *
     * <blockquote>
     *
     * <ol>
     *
     * <li>如果<code>targetType</code>的转换器没有注册,<b>那么传入的value原样返回</b>,<br>
     * 比如<code>CastHelper.convert("zh_CN", Locale.class)</code> 由于找不到converter,那么返回"zh_CN".
     * </li>
     *
     * <li>如果转换不了,会使用默认值</li>
     *
     * <li>如果传的 对象是 <code>toBeConvertedValue.getClass().isArray()</code> 或者 {@link Collection}
     * <blockquote>
     *
     * <dl>
     * <dt>如果 <code>targetType</code> 不是数组</dt>
     * <dd>
     * <p>
     * 那么<span style="color:red">会取第一个元素</span>进行转换,<br>
     * </p>
     * </dd>
     *
     * <dt>如果 <code>targetType</code> 是数组</dt>
     * <dd> 会基于targetType 构造一个数组对象,大小长度就是 对象的大小或者长度, 然后迭代对象依次进行转换</dd>
     *
     * </dl>
     *
     * </blockquote>
     * </li>
     * </ol>
     * </blockquote>
     *
     * @param <T>        the generic type
     * @param obj        需要被转换的对象/值
     * @param targetType 要转成什么类型
     * @return 如果 <code>targetType</code> 是null,抛出 {@link IllegalArgumentException}<br>
     * 如果对象是null,那么直接返回null<br>
     * 否则返回 {@link org.apache.commons.beanutils.ConvertUtils#convert(Object, Class)}
     */
    public static <T> T convert(Object obj, Class<T> targetType) {
        if (targetType == null) {
            throw new IllegalArgumentException("targetType can't be null!");
        }
        return null == obj ? null : (T) ConvertUtils.convert(obj, targetType);
    }

}
