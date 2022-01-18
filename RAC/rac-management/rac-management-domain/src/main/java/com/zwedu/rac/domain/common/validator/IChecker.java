package com.zwedu.rac.domain.common.validator;

import com.zwedu.rac.domain.common.exception.BaseException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;

import static com.zwedu.rac.domain.common.enums.ResponseCodeEnum.UNKNOWN_EXCEPTION;

/**
 * 断言基类
 *
 * @author qingchuan
 * @date 2020/5/13
 */
public interface IChecker {
    /**
     * 获取异常消息
     *
     * @return 异常消息
     */
    String getMsg();

    /**
     * 获取异常码
     *
     * @return 异常码
     */
    default Integer getCode() {
        return UNKNOWN_EXCEPTION.getValue();
    }

    /**
     * 创建异常
     *
     * @param args 参数
     * @return 基础异常
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     *
     * @param throwable 异常
     * @param args      参数
     * @return 基础异常
     */
    default BaseException newException(Throwable throwable, Object... args) {
        String msg = MessageFormat.format(this.getMsg(), args);
        throw new BaseException(UNKNOWN_EXCEPTION.getValue(), msg, throwable);
    }

    /**
     * 参数不为空，为空的话抛出异常
     *
     * @param obj 参数
     */
    default void notNull(Object obj) {
        if ((obj instanceof String && StringUtils.isEmpty(obj.toString()))
                || (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj))
                || (obj instanceof Map && MapUtils.isEmpty((Map) obj))
                || (obj == null)) {
            throw newException();
        }
    }

    /**
     * 参数不为空，为空的话抛出异常
     *
     * @param obj 参数
     */
    default void isNull(Object obj) {
        if (obj != null) {
            throw newException();
        }
    }

    /**
     * 校验对象是否为空，为空的话抛出异常
     *
     * @param obj  被校验对象
     * @param args 参数
     */
    default void notNull(Object obj, Object... args) {
        if ((obj instanceof String && StringUtils.isEmpty(obj.toString()))
                || (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj))
                || (obj instanceof Map && MapUtils.isEmpty((Map) obj))
                || (obj == null)) {
            throw newException(args);
        }
    }

    /**
     * 参数不为空，为空的话抛出异常
     *
     * @param obj 参数
     */
    default void isNull(Object obj, Object... args) {
        if (obj != null) {
            throw newException(args);
        }
    }

    /**
     * 断言条件表达式是否成立，不成立则抛出异常
     *
     * @param condition 校验条件
     * @param args      参数
     */
    default void isTrue(Boolean condition, Object... args) {
        if (!condition) {
            throw newException(args);
        }
    }

    /**
     * 断言条件表达式是否不成立，成立则抛出异常
     *
     * @param condition 校验条件
     * @param args      参数
     */
    default void notTrue(Boolean condition, Object... args) {
        if (condition) {
            throw newException(args);
        }
    }

    /**
     * 校验对象是否为空，为空的话抛出异常
     *
     * @param objects 校验对象数组
     */
    default void allNotNull(Object... objects) {
        for (Object obj : objects) {
            notNull(obj);
        }
    }

    /**
     * 校验对象是否为空，为空的话抛出异常
     *
     * @param objects 校验对象数组
     */
    default void anyNotNull(Object... objects) {
        for (Object obj : objects) {
            if (!((obj instanceof String && StringUtils.isEmpty(obj.toString()))
                    || (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj))
                    || (obj instanceof Map && MapUtils.isEmpty((Map) obj))
                    || (obj == null))) {
                return;
            }
        }
        throw newException();
    }
}

