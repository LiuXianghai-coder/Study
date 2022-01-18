package com.zwedu.rac.domain.common.exception;


import com.zwedu.rac.domain.common.enums.ResponseCodeEnum;

/**
 * exception for validation
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class ValidationException extends BaseException {
    /**
     * 构造方法
     *
     * @param msg 错误信息
     */
    public ValidationException(String msg) {
        super(ResponseCodeEnum.PARAM_ERROR.getValue(), msg);
    }
}
