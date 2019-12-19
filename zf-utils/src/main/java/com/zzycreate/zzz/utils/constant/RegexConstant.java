package com.zzycreate.zzz.utils.constant;

import java.util.regex.Pattern;

/**
 * 校验工具使用到的所有正则表达式
 *
 * @author zzycreate
 * @date 19-2-17
 */
public interface RegexConstant {
    /**
     * 邮箱
     */
    Pattern EMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]*[A-Z0-9]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * 手机号
     */
    Pattern MOBILE = Pattern.compile("^1\\d{10}$");

    /**
     * 中文
     */
    Pattern CHINESE = Pattern.compile("^[\u4e00-\u9fa5]+$");

    /**
     * IPv4
     */
    Pattern IPV4 = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    /**
     * 标准 IPv6
     */
    Pattern IPV6_STD = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    /**
     * 16 进制 IPv6
     */
    Pattern IPV6_HEX =
            Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    /**
     * URL
     */
    Pattern URL = Pattern.compile("^((https|http|ftp|rtsp|mms)?://)[^\\s]+");

    /**
     * 整数
     */
    Pattern INTEGER = Pattern.compile("[0-9]\\d*");

    /**
     * 正浮点数
     */
    Pattern FLOAT = Pattern.compile("[0-9]\\d*.\\d*|0.\\d*[1-9]\\d*");

    /**
     * 数字（整数或浮点数）
     */
    Pattern NUMBER = Pattern.compile("^([+|-]?[0-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])|([0])|([0]\\.\\d*)$");

}
