package com.zwedu.rac.domain.common.exception;

/**
 * 基本异常
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public class BaseException extends RuntimeException {
    /**
     * code
     */
    private int code;
    /**
     * msg
     */
    private String msg;

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(int code, String msg, Throwable throwable) {
        super(throwable);
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
