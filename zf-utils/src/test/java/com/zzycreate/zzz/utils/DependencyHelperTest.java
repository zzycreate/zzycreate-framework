package com.zzycreate.zzz.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link DependencyHelper} 单元测试
 */
public class DependencyHelperTest {

    @Test
    public void hasDependency() {
        Assert.assertTrue(DependencyHelper.hasDependency("java.lang.Integer"));
        Assert.assertFalse(DependencyHelper.hasDependency("java.lang.Test"));
    }

    @Test
    public void hasDependencyElseThrow() {
        try {
            DependencyHelper.hasDependencyElseThrow("java.lang.Integer");
        } catch (ClassNotFoundException e) {
            Assert.assertNull(e);
        }
        try {
            DependencyHelper.hasDependencyElseThrow("java.lang.Test");
        } catch (ClassNotFoundException e) {
            Assert.assertNotNull(e);
        }
    }
}
