package com.zzycreate.zzz.utils.enums;

import lombok.Getter;

/**
 * 响应模型枚举
 *
 * @author zzycreate
 * @date 2018/12/09
 */
@Getter
public enum ResCode {
    /**
     * 成功的响应结果
     */
    SUCCESS("0"),
    /**
     * 失败的响应结果
     */
    FAILURE("-1"),
    /**
     * 系统级异常
     */
    ERROR("1000"),
    /**
     * 系统级异常，参数异常，1001 请求参数不正确
     */
    ERROR_PARAMS("1001"),
    /**
     * 系统级异常，资源权限异常，1401 请求未认证
     */
    UNAUTHORIZED("1401"),
    /**
     * 系统级异常，资源权限异常，1403 无权访问资源，请求被拒绝
     */
    FORBIDDEN("1403"),
    /**
     * 系统级异常，资源权限异常，1404 资源不存在
     */
    NOT_FOUND("1404"),
    /**
     * 应用级异常，资源权限异常，1408 请求资源超时
     */
    TIMEOUT("1408"),
    /**
     * 应用级异常，资源权限异常，1409 请求资源发生冲突
     */
    CONFLICT("1409"),
    /**
     * 系统级异常，资源权限异常，1423 资源被锁定
     */
    LOCKED("1423"),
    /**
     * 系统级异常，资源权限异常，1429 请求过于频繁
     */
    TOO_OFTEN_REQUEST("1429"),
    /**
     * 系统级异常，服务器异常, 1500 服务异常
     */
    INTERNAL_SERVER_ERROR("1500"),
    /**
     * 系统级异常，服务器异常, 1501 服务未实现
     */
    NOT_IMPLEMENTED("1501"),
    /**
     * 系统级异常，服务器异常, 1503 服务不可用
     */
    SERVICE_UNAVAILABLE("1503"),
    ;

    private String code;

    ResCode(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return code;
    }
}
