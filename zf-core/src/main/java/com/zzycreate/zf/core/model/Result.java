package com.zzycreate.zf.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zzycreate.zf.core.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 响应结果封装对象
 *
 * @param <T> 数据类型
 * @author zzycreate
 * @date 2019/08/11
 */
@Data
public final class Result<T> implements Serializable {

    private static final long serialVersionUID = -2938877652303915520L;

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误提示
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     * <p>
     * 因为 A 方法返回的 Result 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T>    返回的泛型
     * @return 新的 Result 对象
     */
    public static <T> Result<T> wrap(Result<?> result) {
        return of(result.getCode(), result.getMsg(), null);
    }

    public static <S, T> Result<T> wrap(Result<S> result, Function<S, T> func) {
        return of(result.getCode(), result.getMsg(), func.apply(result.getData()));
    }

    public static <T> Result<T> error() {
        return error(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMsg(), null);
    }

    public static <T> Result<T> error4msg(String msg) {
        return error(ResultEnum.FAILURE.getCode(), msg, null);
    }

    public static <T> Result<T> error4data(T data) {
        return error(ResultEnum.FAILURE.getCode(), ResultEnum.FAILURE.getMsg(), null);
    }

    public static <T> Result<T> error(String code, String msg) {
        return error(code, msg, null);
    }

    public static <T> Result<T> error(String code, String msg, T data) {
        assert !ResultEnum.SUCCESS.getCode().equals(code) : "code 必须是失败的！";
        return of(code, msg, data);
    }

    public static <T> Result<T> success() {
        return success(ResultEnum.SUCCESS.getMsg(), null);
    }

    public static <T> Result<T> success4msg(String msg) {
        return success(msg, null);
    }

    public static <T> Result<T> success4data(T data) {
        return success(ResultEnum.SUCCESS.getMsg(), data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return of(ResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> of(String code, String msg, T data) {
        Result<T> result = new Result<>();
        result.code = code;
        result.msg = msg;
        result.data = data;
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultEnum.SUCCESS.getCode().equals(code);
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

}
