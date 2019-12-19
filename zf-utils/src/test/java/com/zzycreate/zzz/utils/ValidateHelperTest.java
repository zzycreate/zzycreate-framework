package com.zzycreate.zzz.utils;

import com.zzycreate.zzz.utils.enums.Gender;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
public class ValidateHelperTest {

    @Test
    public void isEmail() {
        assertTrue(ValidateHelper.isEmail("a@b.cd"));
        assertFalse(ValidateHelper.isEmail("a@b.c"));
        assertTrue(ValidateHelper.isEmail("Aa%_-.+@b.cd"));
        assertTrue(ValidateHelper.isEmail("a@b9.cd"));
        assertFalse(ValidateHelper.isEmail("a@b_.cd"));
        assertFalse(ValidateHelper.isEmail("a@b-.cd"));
        assertFalse(ValidateHelper.isEmail("a@b..cd"));
        assertFalse(ValidateHelper.isEmail("a@b.-_x.cd"));
        assertFalse(ValidateHelper.isEmail("a@b.cdefghi"));
        assertFalse(ValidateHelper.isEmail("a@b.cd4f"));
    }

    @Test
    public void isMobile() {
        assertTrue(ValidateHelper.isMobile("11234567890"));
        assertFalse(ValidateHelper.isMobile("1123456789"));
        assertFalse(ValidateHelper.isMobile("112345678900"));
        assertTrue(ValidateHelper.isMobile("19934567890"));
    }

    @Test
    public void isChinese() {
        assertTrue(ValidateHelper.isChinese("中文"));
        assertFalse(ValidateHelper.isChinese("中文1"));
        assertFalse(ValidateHelper.isChinese("中文english"));
        assertFalse(ValidateHelper.isChinese("english"));
    }

    @Test
    public void isIpAddress() {
        assertTrue(ValidateHelper.isIpAddress("127.0.0.1"));
        assertFalse(ValidateHelper.isIpAddress("256.0.0.1"));
        assertFalse(ValidateHelper.isIpAddress("127.0.0.1.1"));

        assertTrue(ValidateHelper.isIpAddress("2001:0db8:85a3:08d3:1319:8a2e:0370:7344"));
        assertTrue(ValidateHelper.isIpAddress("2001:0db8:85a3::1319:8a2e:0370:7344"));
        assertTrue(ValidateHelper.isIpAddress("2001:0DB8:0::0:1428:57ab"));
        assertTrue(ValidateHelper.isIpAddress("2001:0DB8::1428:57ab"));
        assertFalse(ValidateHelper.isIpAddress("2001::25de::cade"));
        assertFalse(ValidateHelper.isIpAddress("2001:0db8:85a3:08d3:1319:8a2e:0370"));
        assertFalse(ValidateHelper.isIpAddress("2001:0db8:85a3:08d3:1319:8a2e:0370:7344:7344"));
    }

    @Test
    public void isIPv4Address() {
        assertTrue(ValidateHelper.isIPv4Address("127.0.0.1"));
        assertFalse(ValidateHelper.isIPv4Address("256.0.0.1"));
        assertFalse(ValidateHelper.isIPv4Address("127.0.0.1.1"));
    }

    @Test
    public void isIPv6Address() {
        assertTrue(ValidateHelper.isIpV6Address("2001:0db8:85a3:08d3:1319:8a2e:0370:7344"));
        assertTrue(ValidateHelper.isIpV6Address("2001:0db8:85a3::1319:8a2e:0370:7344"));
        assertTrue(ValidateHelper.isIpV6Address("2001:0DB8:0::0:1428:57ab"));
        assertTrue(ValidateHelper.isIpV6Address("2001:0DB8::1428:57ab"));
        assertFalse(ValidateHelper.isIpV6Address("2001::25de::cade"));
        assertFalse(ValidateHelper.isIpV6Address("2001:0db8:85a3:08d3:1319:8a2e:0370"));
        assertFalse(ValidateHelper.isIpV6Address("2001:0db8:85a3:08d3:1319:8a2e:0370:7344:7344"));
    }

    @Test
    public void isUrl() {
        assertTrue(ValidateHelper.isUrl("http://zzycreate.com"));
        assertTrue(ValidateHelper.isUrl("http://zzycreate.com/test"));
        assertTrue(ValidateHelper.isUrl("http://zzycreate.com/test?xx=aa"));
        assertTrue(ValidateHelper.isUrl("https://zzycreate.com/test?xx=aa"));
    }

    @Test
    public void isInteger() {
        assertTrue(ValidateHelper.isInteger("1"));
        assertFalse(ValidateHelper.isInteger("1 "));
        assertFalse(ValidateHelper.isInteger(""));
        assertFalse(ValidateHelper.isInteger("123L"));
        assertFalse(ValidateHelper.isInteger("a"));
    }

    @Test
    public void isFloat() {
        assertTrue(ValidateHelper.isFloat("1.2"));
        assertFalse(ValidateHelper.isFloat("1.2 "));
        assertFalse(ValidateHelper.isFloat(""));
        assertFalse(ValidateHelper.isFloat("123.2d"));
        assertFalse(ValidateHelper.isFloat("a"));
    }

    @Test
    public void isNumber() {
        assertTrue(ValidateHelper.isNumber("1"));
        assertFalse(ValidateHelper.isNumber("1 "));
        assertFalse(ValidateHelper.isNumber(""));
        assertFalse(ValidateHelper.isNumber("123L"));
        assertFalse(ValidateHelper.isNumber("a"));

        assertTrue(ValidateHelper.isNumber("1.2"));
        assertFalse(ValidateHelper.isNumber("1.2 "));
        assertFalse(ValidateHelper.isNumber(""));
        assertFalse(ValidateHelper.isNumber("123.2d"));
        assertFalse(ValidateHelper.isNumber("a"));
    }

    @Test
    public void isIdCard() {
        assertTrue(ValidateHelper.isIdCard("37110219751224782X"));
        assertTrue(ValidateHelper.isIdCard("37110219751224782x"));
        assertFalse(ValidateHelper.isIdCard("371102197512247821"));
    }

    @Test
    public void getAgeByIdCard() {
        assertEquals(Calendar.getInstance().get(Calendar.YEAR) - 1975,
                ValidateHelper.getAgeByIdCard("37110219751224782X"));
    }

    @Test
    public void getBirthByIdCard() {
        assertEquals("19751224", ValidateHelper.getBirthByIdCard("37110219751224782X"));
    }

    @Test
    public void getYearByIdCard() {
        assertEquals(1975, ValidateHelper.getYearByIdCard("37110219751224782X").intValue());
    }

    @Test
    public void getMonthByIdCard() {
        assertEquals(12, ValidateHelper.getMonthByIdCard("37110219751224782X").intValue());
    }

    @Test
    public void getDateByIdCard() {
        assertEquals(24, ValidateHelper.getDateByIdCard("37110219751224782X").intValue());
    }

    @Test
    public void getGenderByIdCard() {
        assertEquals(Gender.FEMALE, ValidateHelper.getGenderByIdCard("37110219751224782X"));
    }

    @Test
    public void getProvinceByIdCard() {
        assertEquals("山东", ValidateHelper.getProvinceByIdCard("37110219751224782X"));
    }
}