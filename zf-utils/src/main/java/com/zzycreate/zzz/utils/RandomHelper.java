package com.zzycreate.zzz.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * 随机数工具类
 *
 * @author zzycreate
 * @date 18-12-1
 */
public class RandomHelper {

    /**
     * 随机生成UUID.(长度36位,字母+数字+横杠)
     *
     * @return UUID
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机生成UUID.(长度32位,字母+数字)
     *
     * @return UUID(no '-')
     */
    public static String getShortUuid() {
        return getUuid().replace("-", "");
    }

    /**
     * 获取默认位数（12位）的随机数
     *
     * @return 12 位的随机数
     */
    public static String random() {
        return random(12);
    }

    /**
     * 获取随机数
     * 使用SecureRandom安全的伪随机数
     * {@link org.apache.commons.lang3.RandomStringUtils#randomAlphanumeric(int)}
     *
     * @param size 盐的位数
     * @return 随机盐
     */
    public static String random(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

}
