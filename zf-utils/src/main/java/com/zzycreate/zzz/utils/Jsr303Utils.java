package com.zzycreate.zzz.utils;

import com.zzycreate.zf.core.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zzycreate
 * @date 2019/11/26
 */
public class Jsr303Utils {

    /**
     * JSR303 校验（会抛出BusinessException）
     *
     * @param data 待校验的数据
     * @param <T>  数据类型
     * @throws ServiceException 校验不通过
     */
    public static <T> void validate(T data) throws ServiceException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(data);
        if (CollectionUtils.isNotEmpty(violations)) {
            String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
            throw new ServiceException(msg);
        }
    }

}
