package com.zwedu.rac.domain.common.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * app 断言检测类
 *
 * @author qingchuan
 * @date 2020/5/13
 */
@Getter
@AllArgsConstructor
public enum ParamAssert implements ParamChecker {
    // 参数错误
    PARAM_EMPTY_ERROR("参数为空，请检查参数");

    /**
     * 错误消息
     */
    private String msg;
}