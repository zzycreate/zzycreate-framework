package com.zzycreate.zzz.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 安全工具类
 * 1. 敏感信息遮蔽：手机号隐藏
 *
 * @author zzycreate
 * @date 2019/5/15
 */
public class SecurityHelper {

    private static String PLACEHOLDER_ASTERISK = "*";

    /**
     * 隐藏敏感信息
     *
     * @param info        信息实体
     * @param left        左边保留的字符数
     * @param right       右边保留的字符数
     * @param basedOnLeft 当长度异常时，是否显示左边
     *                    <code>true</code>显示左边，
     *                    <code>false</code>显示右边
     * @param placeholder 占位符，默认为 {@link SecurityHelper#PLACEHOLDER_ASTERISK}
     * @return 处理之后的信息
     */
    public static String hideSensitiveInfo(String info, int left, int right, boolean basedOnLeft, String placeholder) {
        if (StringUtils.isBlank(info)) {
            return "";
        }
        info = info.trim();

        if (StringUtils.isBlank(placeholder)) {
            placeholder = PLACEHOLDER_ASTERISK;
        }
        StringBuilder sb = new StringBuilder();
        int hiddenCharCount = info.length() - left - right;
        int rightIndex = info.length() - right;
        if (hiddenCharCount > 0) {
            String prefix = info.substring(0, left);
            String suffix = info.substring(rightIndex);
            sb.append(prefix);
            for (int i = 0; i < hiddenCharCount; i++) {
                sb.append(placeholder);
            }
            sb.append(suffix);
        } else {
            if (basedOnLeft) {
                if (info.length() > left && left > 0) {
                    sb.append(info, 0, left)
                            .append(placeholder).append(placeholder).append(placeholder).append(placeholder);
                } else {
                    sb.append(info, 0, 1)
                            .append(placeholder).append(placeholder).append(placeholder).append(placeholder);
                }
            } else {
                if (info.length() > right && right > 0) {
                    sb.append(placeholder).append(placeholder).append(placeholder).append(placeholder)
                            .append(info.substring(rightIndex));
                } else {
                    sb.append(placeholder).append(placeholder).append(placeholder).append(placeholder)
                            .append(info.substring(info.length() - 1));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 同上 {@link SecurityHelper#hideSensitiveInfo(String, int, int, boolean, String)}
     *
     * @param info        info
     * @param left        left
     * @param right       right
     * @param basedOnLeft basedOnLeft
     * @return String
     */
    public static String hideSensitiveInfo(String info, int left, int right, boolean basedOnLeft) {
        return hideSensitiveInfo(info, left, right, basedOnLeft, PLACEHOLDER_ASTERISK);
    }

    /**
     * 同上 {@link SecurityHelper#hideSensitiveInfo(String, int, int, boolean, String)}
     *
     * @param info  info
     * @param left  left
     * @param right right
     * @return String
     */
    public static String hideSensitiveInfo(String info, int left, int right) {
        return hideSensitiveInfo(info, left, right, true);
    }

    /**
     * 隐藏手机号
     *
     * @param mobile 手机号
     * @return 隐藏后的手机号
     */
    public static String hideMobile(String mobile) {
        return hideSensitiveInfo(mobile, 3, 4);
    }

}
