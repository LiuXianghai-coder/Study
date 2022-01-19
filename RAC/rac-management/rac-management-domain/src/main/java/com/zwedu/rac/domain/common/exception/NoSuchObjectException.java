package com.zwedu.rac.domain.common.exception;


import com.zwedu.rac.domain.common.enums.ResponseCodeEnum;
import org.slf4j.helpers.MessageFormatter;

/**
 * exception for business
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class NoSuchObjectException extends BaseException {
    
    /**
     * 构造方法
     *
     * @param msg  错误信息
     * @param args 参数
     */
    public NoSuchObjectException(String msg, Object... args) {
        super(ResponseCodeEnum.BIZ_EXCEPTION.getValue(), MessageFormatter.arrayFormat(msg, args).getMessage());
    }
}
