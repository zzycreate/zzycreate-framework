package com.zzycreate.zf.core.enums;

/**
 * 删除标识状态
 *
 * @author zzycreate
 * @date 2019/8/11
 */
public enum DeletedStatusEnum {

    /**
     * 正常(未删除)
     */
    DELETED_NO(0, "正常(未删除)"),
    /**
     * 删除
     */
    DELETED_YES(1, "删除");

    /**
     * 状态值
     */
    private Integer code;
    /**
     * 状态名
     */
    private String value;

    DeletedStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static DeletedStatusEnum ofCode(Integer code) {
        if (code == null) {
            return DeletedStatusEnum.DELETED_NO;
        }
        for (DeletedStatusEnum statusEnum : DeletedStatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return DeletedStatusEnum.DELETED_NO;
    }

}
