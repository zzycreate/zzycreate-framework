package com.zzycreate.zf.core.enums;

import lombok.Getter;

/**
 * @author zzycreate
 * @date 2019/11/26
 */
@Getter
public enum YesOrNoEnums {

    /**
     * YES/NO
     */
    INT_1(1, true),
    INT_0(0, false),
    TRUE(true, true),
    FALSE(false, false),
    TRUE_STR("true", true),
    FALSE_STR("false", false),
    Y_STR("Y", true),
    N_STR("N", false),
    ;

    private Object value;
    private boolean yes;

    YesOrNoEnums(Object value, boolean yes) {
        this.value = value;
        this.yes = yes;
    }

    public static YesOrNoEnums ofValue(Object obj) {
        if (obj == null) {
            return null;
        }
        for (YesOrNoEnums value : YesOrNoEnums.values()) {
            if (value.getValue().equals(obj)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 检查对象是否代表YES
     *
     * @param obj 待检查的对象
     * @return YES
     */
    public static boolean checkYes(Object obj) {
        if (obj == null) {
            return false;
        }
        for (YesOrNoEnums value : YesOrNoEnums.values()) {
            if (value.getValue().equals(obj)) {
                return value.isYes();
            }
        }
        return false;
    }

    /**
     * 检查对象是否在枚举中（对象与枚举的value属性一致）
     *
     * @param obj 待检查的对象
     * @return 对象是否在枚举中
     */
    public static boolean inEnums(Object obj) {
        for (YesOrNoEnums value : YesOrNoEnums.values()) {
            if (value.getValue().equals(obj)) {
                return true;
            }
        }
        return false;
    }
}
