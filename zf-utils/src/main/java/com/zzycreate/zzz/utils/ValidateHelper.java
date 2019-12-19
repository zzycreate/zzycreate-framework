package com.zzycreate.zzz.utils;


import com.zzycreate.zzz.utils.constant.RegexConstant;
import com.zzycreate.zzz.utils.enums.Gender;

/**
 * 校验工具类
 *
 * @author zzycreate
 * @date 18-12-1
 */
public class ValidateHelper {

    /**
     * 是否是合法邮箱格式
     *
     * @param email 邮件
     * @return 校验结果
     */
    public static boolean isEmail(String email) {
        return RegexConstant.EMAIL.matcher(email).matches();
    }

    /**
     * 是否是合法手机号格式
     *
     * @param mobile 手机号
     * @return 校验结果
     */
    public static boolean isMobile(String mobile) {
        return RegexConstant.MOBILE.matcher(mobile).matches();
    }

    /**
     * 是否是汉字
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isChinese(String str) {
        return RegexConstant.CHINESE.matcher(str).matches();
    }

    /**
     * 是否是IP地址（IPv4或者IPv6）
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isIpAddress(String str) {
        return isIPv4Address(str) || isIpV6Address(str);
    }

    /**
     * 是否是IPv4
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isIPv4Address(String str) {
        return RegexConstant.IPV4.matcher(str).matches();
    }

    /**
     * 是否是IPv6
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isIpV6Address(String str) {
        return isIpV6StdAddress(str) || isIpV6HexCompressedAddress(str);
    }

    /**
     * 是否是标准 IPv6 地址
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    private static boolean isIpV6StdAddress(String str) {
        return RegexConstant.IPV6_STD.matcher(str).matches();
    }

    /**
     * 是否是 16进制 IPv6 地址
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    private static boolean isIpV6HexCompressedAddress(String str) {
        return RegexConstant.IPV6_HEX.matcher(str).matches();
    }

    /**
     * 是否是URL
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isUrl(String str) {
        return RegexConstant.URL.matcher(str).matches();
    }

    /**
     * 是否是整数
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isInteger(String str) {
        return RegexConstant.INTEGER.matcher(str).matches();
    }

    /**
     * 是否是正浮点数
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isFloat(String str) {
        return RegexConstant.FLOAT.matcher(str).matches();
    }

    /**
     * 是否是整数或者浮点数
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isNumber(String str) {
        return RegexConstant.NUMBER.matcher(str).matches();
    }

    /**
     * 是否是身份证号
     *
     * @param str 待校验字符串
     * @return 校验结果
     */
    public static boolean isIdCard(String str) {
        return IdCardHelper.validateIdCard(str);
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idNumber 身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idNumber) {
        Integer age = IdCardHelper.getAgeByIdCard(idNumber);
        return age == null ? 0 : age;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idNumber 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idNumber) {
        return IdCardHelper.getBirthByIdCard(idNumber);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idNumber 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idNumber) {
        return IdCardHelper.getYearByIdCard(idNumber);
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idNumber 身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idNumber) {
        return IdCardHelper.getMonthByIdCard(idNumber);
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idNumber 身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdCard(String idNumber) {
        return IdCardHelper.getDateByIdCard(idNumber);
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idNumber 身份编号
     * @return 性别(M - 男 ， F - 女 ， N - 未知)
     */
    public static Gender getGenderByIdCard(String idNumber) {
        return IdCardHelper.getGenderByIdCard(idNumber);
    }

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idNumber 身份编码
     * @return 省级编码。
     */
    public static String getProvinceByIdCard(String idNumber) {
        return IdCardHelper.getProvinceByIdCard(idNumber);
    }

}
