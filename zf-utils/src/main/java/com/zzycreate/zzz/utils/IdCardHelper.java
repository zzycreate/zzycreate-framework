package com.zzycreate.zzz.utils;

import com.zzycreate.zzz.utils.enums.Gender;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证工具类
 *
 * @author Based By June  http://www.oschina.net/code/snippet_1611_2881
 */
public class IdCardHelper {

    /**
     * 中国公民身份证号码最小长度。
     */
    private static final int CHINA_ID_MIN_LENGTH = 15;

    /**
     * 中国公民身份证号码最大长度。
     */
    private static final int CHINA_ID_MAX_LENGTH = 18;

    /**
     * 最低年限
     */
    private static final int MIN = 1930;

    /**
     * 每位加权因子
     */
    private static final int[] POWER = {
            7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2
    };

    /**
     * 第18位校检码
     */
    public static final String[] VERIFY_CODE = {
            "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"
    };

    /**
     * 城市代码
     */
    private static Map<String, String> cityCodes = new HashMap<>();

    static {
        cityCodes.put("11", "北京");
        cityCodes.put("12", "天津");
        cityCodes.put("13", "河北");
        cityCodes.put("14", "山西");
        cityCodes.put("15", "内蒙古");
        cityCodes.put("21", "辽宁");
        cityCodes.put("22", "吉林");
        cityCodes.put("23", "黑龙江");
        cityCodes.put("31", "上海");
        cityCodes.put("32", "江苏");
        cityCodes.put("33", "浙江");
        cityCodes.put("34", "安徽");
        cityCodes.put("35", "福建");
        cityCodes.put("36", "江西");
        cityCodes.put("37", "山东");
        cityCodes.put("41", "河南");
        cityCodes.put("42", "湖北");
        cityCodes.put("43", "湖南");
        cityCodes.put("44", "广东");
        cityCodes.put("45", "广西");
        cityCodes.put("46", "海南");
        cityCodes.put("50", "重庆");
        cityCodes.put("51", "四川");
        cityCodes.put("52", "贵州");
        cityCodes.put("53", "云南");
        cityCodes.put("54", "西藏");
        cityCodes.put("61", "陕西");
        cityCodes.put("62", "甘肃");
        cityCodes.put("63", "青海");
        cityCodes.put("64", "宁夏");
        cityCodes.put("65", "新疆");
        cityCodes.put("71", "台湾");
        cityCodes.put("81", "香港");
        cityCodes.put("82", "澳门");
        cityCodes.put("91", "国外");
    }



    /**
     * 验证身份证是否合法
     */
    public static boolean validateIdCard(String idCard) {
        String card = idCard.trim();
        return validateIdCard18(card) || validateIdCard15(card);
    }

    /**
     * 验证18位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    private static boolean validateIdCard18(String idCard) {
        if (idCard.length() != CHINA_ID_MAX_LENGTH) {
            return false;
        }
        // 前17位
        String code17 = idCard.substring(0, 17);
        // 第18位
        String code18 = idCard.substring(17);
        if (!isNum(code17)) {
            return false;
        }
        int[] iCard = converCharToInt(code17.toCharArray());
        int powerSum = getPowerSum(iCard);
        // 获取校验位
        String checkCode = getCheckCode(powerSum);
        if (checkCode.length() <= 0) {
            return false;
        }
        return checkCode.equalsIgnoreCase(code18);
    }

    /**
     * 验证15位身份编码是否合法
     *
     * @param idCard 身份编码
     * @return 是否合法
     */
    private static boolean validateIdCard15(String idCard) {
        if (idCard.length() != CHINA_ID_MIN_LENGTH) {
            return false;
        }
        if (!isNum(idCard)) {
            return false;
        }
        // 前两位为城市编码
        String proCode = idCard.substring(0, 2);
        if (!cityCodes.containsKey(proCode)) {
            return false;
        }
        // 6-12位为年月日
        String yymmdd = idCard.substring(6, 12);
        Date birthDate;
        try {
            birthDate = new SimpleDateFormat("yy").parse(yymmdd.substring(0, 2));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        Calendar cal = Calendar.getInstance();
        if (birthDate != null) {
            cal.setTime(birthDate);
        }
        int year = cal.get(Calendar.YEAR);
        int month = Integer.parseInt(yymmdd.substring(2, 4));
        int day = Integer.parseInt(yymmdd.substring(4, 6));
        return validateDate(year, month, day);
    }

    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCard15 15位身份编码
     * @return 18位身份编码， 如果转换不成功返回null
     */
    private static String convert15CardTo18(String idCard15) {
        if (idCard15.length() != CHINA_ID_MIN_LENGTH) {
            return null;
        }
        if (!isNum(idCard15)) {
            return null;
        }

        // 获取15位身份证的年月日6位值
        String yymmdd = idCard15.substring(6, 12);
        Date date;
        try {
            date = new SimpleDateFormat("yyMMdd").parse(yymmdd);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        // 获取出生年(完全表现形式,如：2010)
        String sYear = String.valueOf(calendar.get(Calendar.YEAR));
        String idCard17 = idCard15.substring(0, 6) + sYear + idCard15.substring(8);
        // 转换字符数组
        int[] iCardArr = converCharToInt(idCard17.toCharArray());
        // 计算加权值
        int powerSum = getPowerSum(iCardArr);
        // 获取校验位
        String checkCode = getCheckCode(powerSum);
        if (checkCode.length() <= 0) {
            return null;
        }
        return idCard17 + checkCode;
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param idCard 身份编号
     * @return 年龄, 如果没有获取到，则返回 null
     */
    public static Integer getAgeByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        if (idCard == null) {
            return null;
        }
        String year = idCard.substring(6, 10);
        Calendar cal = Calendar.getInstance();
        int iCurrYear = cal.get(Calendar.YEAR);
        return iCurrYear - Integer.parseInt(year);
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd), 如果获取失败返回null
     */
    public static String getBirthByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        return idCard == null ? null : idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        return idCard == null ? null : Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard 身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        return idCard == null ? null : Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard 身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        return idCard == null ? null : Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别
     */
    public static Gender getGenderByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        String sCardNum = idCard.substring(16, 17);
        switch (Integer.parseInt(sCardNum) % 2) {
            case 0:
                return Gender.FEMALE;
            case 1:
                return Gender.MALE;
            default:
                return null;
        }
    }

    /**
     * 根据身份编号获取户籍省份
     *
     * @param idCard 身份编码
     * @return 省级名称。
     */
    public static String getProvinceByIdCard(String idCard) {
        idCard = getIdCard18(idCard);
        if (idCard == null) {
            return null;
        }
        return cityCodes.get(idCard.substring(0, 2));
    }

    /**
     * 数字验证
     *
     * @param val 待校验的字符串
     * @return 提取的数字。
     */
    private static boolean isNum(String val) {
        return val != null && !"".equals(val) && val.matches("^[0-9]*$");
    }

    /**
     * 验证小于当前日期 是否有效
     *
     * @param year  待验证日期(年)
     * @param month 待验证日期(月 1-12)
     * @param day   待验证日期(日)
     * @return 是否有效
     */
    private static boolean validateDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        int thisYear = cal.get(Calendar.YEAR);
        int datePerMonth;
        // 校验年 1930 - 今年
        if (year < MIN || year >= thisYear) {
            return false;
        }
        // 校验月
        if (month < 1 || month > 12) {
            return false;
        }
        // 校验日
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                datePerMonth = 30;
                break;
            case 2:
                // 闰年
                boolean dm = (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) && year > MIN;
                datePerMonth = dm ? 29 : 28;
                break;
            default:
                datePerMonth = 31;
        }
        return day >= 1 && day <= datePerMonth;
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param arr 身份证前17位的数字数组
     * @return 身份证编码。
     */
    private static int getPowerSum(int[] arr) {
        int iSum = 0;
        if (arr == null || POWER.length != arr.length) {
            return iSum;
        }
        for (int i = 0; i < arr.length; i++) {
            iSum = iSum + arr[i] * POWER[i];
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param powerSum 加权计算结果
     * @return 校验位
     */
    private static String getCheckCode(int powerSum) {
        int i = powerSum % 11;
        return VERIFY_CODE[i];
    }

    /**
     * 获取18位身份证号码, 如果是15位则转18位
     *
     * @param idCard 待校验的身份证号
     * @return 18位身份证号码，如果校验失败返回null
     */
    private static String getIdCard18(String idCard) {
        if (idCard == null) {
            return null;
        }
        if (idCard.length() == CHINA_ID_MIN_LENGTH) {
            idCard = convert15CardTo18(idCard);
        }
        if (idCard == null || idCard.length() < CHINA_ID_MIN_LENGTH) {
            return null;
        }
        return idCard;
    }

    /**
     * 将字符数组转换成数字数组
     *
     * @param ch 字符数组
     * @return 数字数组
     */
    private static int[] converCharToInt(char[] ch) {
        int len = ch.length;
        int[] iArr = new int[len];
        try {
            for (int i = 0; i < len; i++) {
                iArr[i] = Integer.parseInt(String.valueOf(ch[i]));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        return iArr;
    }

}
