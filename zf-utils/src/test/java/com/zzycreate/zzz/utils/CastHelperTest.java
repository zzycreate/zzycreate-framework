package com.zzycreate.zzz.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * {@link CastHelper} 的单元测试类
 *
 * @author zzycreate
 * @date 2018/12/09
 */
@Slf4j
public class CastHelperTest {

    /**
     * 测试 {@link CastHelper#wrapClass(Class)}
     */
    @Test
    public void wrapClass() {
        assertEquals(Character.class, CastHelper.wrapClass(char.class));
        assertEquals(Byte.class, CastHelper.wrapClass(byte.class));
        assertEquals(Integer.class, CastHelper.wrapClass(int.class));
        assertEquals(Short.class, CastHelper.wrapClass(short.class));
        assertEquals(Long.class, CastHelper.wrapClass(long.class));
        assertEquals(Float.class, CastHelper.wrapClass(float.class));
        assertEquals(Double.class, CastHelper.wrapClass(double.class));
        assertEquals(Boolean.class, CastHelper.wrapClass(boolean.class));
        assertEquals(String.class, CastHelper.wrapClass(String.class));

    }

    /**
     * 测试 {@link CastHelper#wrap(Object)}
     */
    @Test
    public void wrap() {
        assertNull(CastHelper.wrap(null));
        assertEquals(Character.class, CastHelper.wrap('c'));
        byte b = 3;
        assertEquals(Byte.class, CastHelper.wrap(b));
        assertEquals(Integer.class, CastHelper.wrap(10));
        short s = 12;
        assertEquals(Short.class, CastHelper.wrap(s));
        assertEquals(Long.class, CastHelper.wrap(123L));
        assertEquals(Float.class, CastHelper.wrap(123.45f));
        assertEquals(Double.class, CastHelper.wrap(145.24));
        assertEquals(Boolean.class, CastHelper.wrap(true));
        assertEquals(String.class, CastHelper.wrap("drytfg"));
        assertEquals(Integer.class, CastHelper.wrap(new Integer(456)));

        log.debug("CastHelper#wrap(Class) -> JUnit 单元测试完毕");
    }

    /**
     * 测试 {@link CastHelper#is(Object, Class)}
     */
    @Test
    public void is() {
        assertFalse(CastHelper.is(null, null));
        assertTrue(CastHelper.is("字符串", String.class));
        assertTrue(CastHelper.is(1, int.class));
        assertTrue(CastHelper.is(10000000, int.class));
        assertTrue(CastHelper.is(1000L, long.class));
        assertTrue(CastHelper.is(1.0f, float.class));
        assertTrue(CastHelper.is(1.23, double.class));
        assertTrue(CastHelper.is(1, Integer.class));
        assertTrue(CastHelper.is(1.23, Double.class));
        assertTrue(CastHelper.is(new String[]{""}, String[].class));
        assertTrue(CastHelper.is(new ArrayList<String>(), List.class));
        assertFalse(CastHelper.is(new ArrayList<String>(), LinkedList.class));
        assertTrue(CastHelper.is(new HashMap<String, Object>(), Map.class));
        log.debug("CastHelper#is(Object, Class) -> JUnit 单元测试完毕");
    }

    /**
     * 测试 {@link CastHelper#isArray(Object)}
     */
    @Test
    public void isArray() {
        Assert.assertTrue(CastHelper.isArray(new String[]{""}));
        Assert.assertFalse(CastHelper.isArray(new ArrayList<>()));
        log.debug("CastHelper#isArray(Object) -> JUnit 单元测试完毕");
    }

    /**
     * 测试 {@link CastHelper#isCollection(Object)}
     */
    @Test
    public void isCollection() {
        Assert.assertFalse(CastHelper.isCollection(new String[]{""}));
        Assert.assertFalse(CastHelper.isCollection(new HashMap<>()));
        Assert.assertTrue(CastHelper.isCollection(new ArrayList<>()));
        log.debug("CastHelper#isCollection(Object) -> JUnit 单元测试完毕");
    }

    @Test
    public void toBoolean() {
        assertNull(CastHelper.toBoolean(null));
        assertTrue(CastHelper.toBoolean(1L));
        assertTrue(CastHelper.toBoolean("1"));
        assertNull(CastHelper.toBoolean("9"));
        assertNull(CastHelper.toBoolean("1,2,3"));

        assertTrue(CastHelper.toBoolean("true"));
        assertTrue(CastHelper.toBoolean("yes"));
        assertTrue(CastHelper.toBoolean("y"));
        assertTrue(CastHelper.toBoolean("on"));
        assertTrue(CastHelper.toBoolean("Y"));
        assertTrue(CastHelper.toBoolean("YeS"));

        assertFalse(CastHelper.toBoolean("false"));
        assertFalse(CastHelper.toBoolean("no"));
        assertFalse(CastHelper.toBoolean("n"));
        assertFalse(CastHelper.toBoolean("off"));
        assertFalse(CastHelper.toBoolean("0"));
        assertFalse(CastHelper.toBoolean("ofF"));

    }

    @Test
    public void toBoolean1() {
        assertTrue(CastHelper.toBoolean("haha", new BooleanConverter(new String[]{"haha"}, new String[]{"hehe"}, null)));
        assertFalse(CastHelper.toBoolean("hehe", new BooleanConverter(new String[]{"haha"}, new String[]{"hehe"}, null)));
        assertTrue(CastHelper.toBoolean("YeS", null));
    }

    @Test
    public void toInteger() {
        assertNull(CastHelper.toInteger(null));
        assertNull(CastHelper.toInteger("aaaa"));
        assertEquals(8, CastHelper.toInteger(8L).intValue());
        assertEquals(8, CastHelper.toInteger("8").intValue());
        assertEquals(8, CastHelper.toInteger(new BigDecimal("8")).intValue());
        assertEquals(1, CastHelper.toInteger(new String[]{"1", "2", "3"}).intValue());
        assertEquals(1, CastHelper.toInteger(CastHelper.toList("1", "2")).intValue());
    }

    @Test
    public void toInteger1() {
        assertNull(CastHelper.toInteger(null, null));
        assertEquals(1, CastHelper.toInteger(null, 1).intValue());
        assertEquals(1, CastHelper.toInteger("aaaa", 1).intValue());
        assertEquals(8, CastHelper.toInteger(8L, 1).intValue());
        assertEquals(8, CastHelper.toInteger("8", 1).intValue());
        assertEquals(8, CastHelper.toInteger(new BigDecimal("8"), 1).intValue());
        assertEquals(1, CastHelper.toInteger(new String[]{"1", "2", "3"}, 8).intValue());
        assertEquals(1, CastHelper.toInteger(CastHelper.toList("1", "2"), 8).intValue());
    }

    @Test
    public void toLong() {
        assertNull(CastHelper.toLong(null));
        assertNull(CastHelper.toLong("aaaa"));
        assertEquals(8L, CastHelper.toLong(8).longValue());
        assertEquals(8L, CastHelper.toLong("8").longValue());
        assertEquals(8L, CastHelper.toLong(new BigDecimal("8")).longValue());
        assertEquals(1L, CastHelper.toLong(new String[]{"1", "2", "3"}).longValue());
        assertEquals(1L, CastHelper.toLong(CastHelper.toList("1", "2")).longValue());
    }

    @Test
    public void toLong1() {
        assertEquals(1L, CastHelper.toLong(null, 1L).longValue());
    }

    @Test
    public void toBigDecimal() {
        assertNull(CastHelper.toBigDecimal(null));
        assertNull(CastHelper.toBigDecimal("aaaa"));
        assertEquals(8, CastHelper.toBigDecimal(8).intValue());
        assertEquals(8, CastHelper.toBigDecimal("8").intValue());
        assertEquals(8, CastHelper.toBigDecimal(new BigDecimal("8")).intValue());
    }

    @Test
    public void toBigDecimal1() {
        assertEquals(1, CastHelper.toBigDecimal("aaaa", new BigDecimal(1)).intValue());
    }

    @Test
    public void toIntegerArray() {
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray(new String[]{"1", "2", "3"}));
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray(CastHelper.toList("1", "2", "3")));

        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray("1,2,3"));
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray("{1,2,3}"));
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray("{ 1 ,2,3}"));
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray("1,2, 3"));
        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray("1,2 , 3"));

        assertArrayEquals(new Integer[]{1, 2, 3}, CastHelper.toIntegerArray(CastHelper.toList("1", "2", " 3")));
        assertArrayEquals(new Integer[]{1, 0, 0}, CastHelper.toIntegerArray(CastHelper.toArray(true, false, false)));
        assertArrayEquals(new Integer[]{}, CastHelper.toIntegerArray(new String[]{"1", null, "2", "3"}));

    }

    @Test
    public void toLongArray() {
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray(new String[]{"1", "2", "3"}));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray(CastHelper.toList("1", "2", "3")));

        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray("1,2,3"));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray("{1,2,3}"));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray("{ 1 ,2,3}"));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray("1,2, 3"));
        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray("1,2 , 3"));

        assertArrayEquals(new Long[]{1L, 2L, 3L}, CastHelper.toLongArray(CastHelper.toList("1", "2", " 3")));
        assertArrayEquals(new Long[]{1L, 0L, 0L}, CastHelper.toLongArray(CastHelper.toArray(true, false, false)));
        assertArrayEquals(new Long[]{}, CastHelper.toLongArray(new String[]{"1", null, "2", "3"}));
    }

    @Test
    public void toEnumeration() {
        assertEquals(CastHelper.toEnumeration(null), Collections.emptyEnumeration());
    }

    @Test
    public void toList() {
        List<Object> list = CastHelper.toList(CastHelper.toEnumeration(null));
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    public void toList1() {
        Set<String> set = new LinkedHashSet<>();
        List<String> list = CastHelper.toList(set);
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    @Test
    public void toList2() {
        List<Integer> list = CastHelper.toList(1, 2, 3);
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    @Test
    public void toSet() {
        List<String> list = new ArrayList<>();
        Set<String> set = CastHelper.toSet(list);
        assertNotNull(set);
        assertTrue(set.isEmpty());
    }

    @Test
    public void toSet1() {
        Set<Integer> set = CastHelper.toSet(1, 2, 3);
        assertNotNull(set);
        assertFalse(set.isEmpty());
    }

    @Test
    public void toArray() {
        assertArrayEquals(new String[]{"1", "2"}, CastHelper.toArray("1", "2"));
        assertArrayEquals(new String[]{}, CastHelper.<String>toArray());
        assertArrayEquals(new Integer[]{}, CastHelper.<Integer>toArray());
        assertNull(CastHelper.toArray(null));
        assertArrayEquals(new String[]{null}, CastHelper.toArray((String) null));
    }

    @Test
    public void toArray1() {
        List<String> list = CastHelper.toList("xinge", "feilong");
        String[] array = CastHelper.toArray(list, String.class);
        assertArrayEquals(new String[]{"xinge", "feilong"}, array);
    }

    @Test
    public void toArray2() {
        String[] array = CastHelper.toArray(new String[]{"2", "1"}, String.class);
        Long[] array1 = CastHelper.toArray(new String[]{"2", "1"}, Long.class);
        assertArrayEquals(new String[]{"2", "1"}, array);
        assertArrayEquals(new Long[]{2L, 1L}, array1);
        assertNull(CastHelper.toArray((String[]) null, Serializable.class));
    }

    @Test
    public void convert() {
        assertEquals(1, CastHelper.convert("1", Integer.class).intValue());
        assertNull(CastHelper.convert("", Integer.class));
        assertEquals(1, CastHelper.convert("1", Long.class).intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertException(){
        CastHelper.convert("1", null);
    }

}
