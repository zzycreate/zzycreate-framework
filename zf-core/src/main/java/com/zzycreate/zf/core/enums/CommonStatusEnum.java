package com.zzycreate.zf.core.enums;

import java.util.Arrays;

/**
 * @author zzycreate
 * @date 2019/8/11
 */
public enum CommonStatusEnum {

    /**
     * 开启-1
     */
    ENABLE(1, "开启"),
    /**
     * 关闭-0
     */
    DISABLE(0, "关闭");

    private static final int[] ALL_CODES = Arrays.stream(CommonStatusEnum.values())
        .mapToInt(CommonStatusEnum::getCode).toArray();

    /**
     * 状态值
     */
    private Integer code;
    /**
     * 状态名
     */
    private String value;

    CommonStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public int[] codes() {
        return ALL_CODES;
    }

}
