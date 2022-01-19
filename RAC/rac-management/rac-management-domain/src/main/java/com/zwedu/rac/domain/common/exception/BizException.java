package com.zwedu.rac.domain.common.exception;


/**
 * exception for business
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BizException extends BaseException {
    /**
     * 构造方法
     *
     * @param code 编码
     * @param msg  错误信息
     */
    public BizException(Integer code, String msg) {
        super(code, msg);
    }
}
