package com.zzycreate.zzz.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
public class RandomHelperTest {

    @Test
    public void getUuid() {
        assertNotNull(RandomHelper.getUuid());
        assertEquals(36, RandomHelper.getUuid().length());
    }

    @Test
    public void getShortUuid() {
        assertNotNull(RandomHelper.getShortUuid());
        assertEquals(32, RandomHelper.getShortUuid().length());
    }

    @Test
    public void random() {
        assertNotNull(RandomHelper.random());
        assertEquals(12, RandomHelper.random().length());
    }

    @Test
    public void testRandom() {
        assertNotNull(RandomHelper.random(18));
        assertEquals(18, RandomHelper.random(18).length());
    }
}