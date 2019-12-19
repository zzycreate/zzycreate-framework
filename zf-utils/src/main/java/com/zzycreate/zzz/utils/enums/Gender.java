package com.zzycreate.zzz.utils.enums;

/**
 * 性别
 *
 * @author zzycreate
 * @date 2018/12/1
 */
public enum Gender {

    /**
     * 男性
     */
    MALE(1),

    /**
     * 女性
     */
    FEMALE(0),
    ;

    private int code;

    Gender(int code) {
        this.code = code;
    }

    /**
     * 获取code
     *
     * @return 性别code
     */
    public int code() {
        return code;
    }
}
