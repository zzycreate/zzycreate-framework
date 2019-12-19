package com.zzycreate.zf.core.enums;

import lombok.Getter;

/**
 * @author zzycreate
 * @date 2019/8/12
 */
@Getter
public enum ResultEnum {

    /**
     * 成功结果
     */
    SUCCESS("0000", "SUCCESS"),
    /**
     * 失败结果
     */
    FAILURE("9999", "FAILURE"),
    ;

    private String code;
    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
