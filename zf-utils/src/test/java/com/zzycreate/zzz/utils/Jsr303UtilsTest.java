package com.zzycreate.zzz.utils;

import com.zzycreate.zf.core.exception.ServiceException;
import lombok.Data;
import org.junit.Test;

import javax.validation.constraints.NotNull;

import static org.junit.Assert.assertTrue;

/**
 * @author zzycreate
 * @date 2019/12/20
 */
public class Jsr303UtilsTest {

    @Test(expected = ServiceException.class)
    public void testValidate() {
        JsrBean jsrBean = new JsrBean();
        Jsr303Utils.validate(jsrBean);
        assertTrue(false);
    }

    @Data
    static class JsrBean {
        @NotNull
        private String key;
    }
}