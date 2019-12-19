package com.zzycreate.zzz.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zzycreate
 * @date 2019/9/26
 */
public class SecurityHelperTest {

    @Test
    public void testHideMobile(){
        String telphone = "18912345678";
        String hideTelphone = "189****5678";
        Assert.assertEquals(hideTelphone, SecurityHelper.hideMobile(telphone));
        Assert.assertEquals(hideTelphone, SecurityHelper.hideSensitiveInfo(telphone, 3, 4));

        Assert.assertEquals("", SecurityHelper.hideSensitiveInfo(null, 3, 4));
        Assert.assertEquals("", SecurityHelper.hideSensitiveInfo("", 3, 4));

        String hideTelphone1 = "189----5678";
        Assert.assertEquals(hideTelphone1, SecurityHelper.hideSensitiveInfo(telphone, 3, 4, true, "-"));
        Assert.assertEquals(hideTelphone, SecurityHelper.hideSensitiveInfo(telphone, 3, 4, true, null));
    }

}
