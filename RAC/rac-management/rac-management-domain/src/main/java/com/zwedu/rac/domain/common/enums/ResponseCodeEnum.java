package com.zwedu.rac.domain.common.enums;

/**
 * 结果枚举
 *
 * @author qingchuan
 * @date 2020/12/9
 */
public enum ResponseCodeEnum {

    SUCCESS(0, "SUCCESS"),
    FAIL(-1, "ERROR"),
    PARAM_ERROR(100, "入参错误"),
    NO_AUTH(403, "无权操作"),
    SYSTEM_EXCEPTION(500, "系统异常"),
    BIZ_EXCEPTION(600, "业务异常"),
    UNKNOWN_EXCEPTION(-2, "未知异常");
    /**
     * code
     */
    private int value;
    /**
     * 描述
     */
    private String text;

    private ResponseCodeEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }
}
