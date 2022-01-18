package com.zwedu.rac.domain.common.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 業務層检测类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Getter
@AllArgsConstructor
public enum BizAssert implements BizChecker {
    // 参数错误
    PARAM_EMPTY_ERROR(1001, "{}不能为空"),
    DATA_TOO_LONG_50_ERROR(1002, "{}长度不能超过50字符"),
    DATA_TOO_LONG_255_ERROR(1003, "{}长度不能超过255字符"),
    DATA_TOO_LONG_1000_ERROR(1004, "{}长度不能超过1000字符"),
    DATA_TOO_LONG_4000_ERROR(1005, "{}长度不能超过4000字符"),
    DATA_PATTERN_ERROR(1006, "{}格式不正确"),
    INCORRECT_VALUE_ERROR(1007, "{}值不正确"),
    EXISTED_VALUE_ERROR(1008, "{}已存在"),
    NOT_EXIST_RECORD_ERROR(1009, "{}不存在"),
    DUPLICATE_RECORD_ERROR(1010, "{}已存在"),
    LOOP_TREE_ERROR(1011, "父节点不能选择自身或子节点"),
    NO_MATCH_BIZLINE_ERROR(1012, "{}业务线不匹配"),
    URL_REPEAT_ERROR(1013, "功能URL '{}' 与 '{}' 重复")
    ;

    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String msg;
}