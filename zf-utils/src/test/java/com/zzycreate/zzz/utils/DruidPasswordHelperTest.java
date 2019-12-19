package com.zzycreate.zzz.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author zzycreate
 * @date 2019/12/18
 */
@Slf4j
public class DruidPasswordHelperTest {
    @Test
    public void test() {
        Map<String, String> map = DruidPasswordHelper.encrypt("1234.abcd");
        assertNotNull(map);
        log.info("{}", map);
        assertNotNull(map.get(DruidPasswordHelper.MAP_PRIVATE_KEY));
        assertNotNull(map.get(DruidPasswordHelper.MAP_PUBLIC_KEY));
        assertNotNull(map.get(DruidPasswordHelper.MAP_PASSWORD));

        assertEquals("1234.abcd", DruidPasswordHelper.decrypt(map.get(DruidPasswordHelper.MAP_PUBLIC_KEY),
                map.get(DruidPasswordHelper.MAP_PASSWORD)));
    }
}
