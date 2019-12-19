package com.zzycreate.zzz.utils;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * {@link PropertiesHelper} 的junit单元测试
 *
 * @author zhaozhenyao
 * @date 2018/4/20
 */
public class PropertiesHelperTest {

    @Test
    public void get() {
        String value = PropertiesHelper.getInstance("config/test").get("test.key");
        TestCase.assertEquals("value", value);
    }

    @Test
    public void getString() {
        String value = PropertiesHelper.getInstance("config/test").getString("test.key");
        TestCase.assertEquals("value", value);
    }

    @Test
    public void getInteger() {
        int value = PropertiesHelper.getInstance("config/test").getInteger("test.int");
        TestCase.assertEquals(1, value);
    }

    @Test
    public void getDouble() {
        double value = PropertiesHelper.getInstance("config/test").getDouble("test.double");
        TestCase.assertEquals(9.9, value);
    }

    @Test
    public void getBoolean() {
        boolean value = PropertiesHelper.getInstance("config/test").getBoolean("test.boolean");
        TestCase.assertTrue(value);
    }
}
