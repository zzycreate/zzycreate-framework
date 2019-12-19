package com.zzycreate.zf.core.enums;

import lombok.Getter;

/**
 * @author zzycreate
 * @date 2019/8/12
 */
@Getter
public enum SortEnum {
    /**
     * 升序
     */
    ASC("asc", "升序"),
    /**
     * 降序
     */
    DESC("desc", "降序"),
    ;

    private String code;
    private String value;

    SortEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

}
