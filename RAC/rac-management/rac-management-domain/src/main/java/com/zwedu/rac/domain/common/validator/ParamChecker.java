package com.zwedu.rac.domain.common.validator;

import com.zwedu.rac.domain.common.exception.ValidationException;
import org.slf4j.helpers.MessageFormatter;


/***
 * 校验断言器
 *
 * @author qingchuan
 * @date 2020/5/13
 */
public interface ParamChecker extends IChecker {

    @Override
    default ValidationException newException(Object... args) {
        String msg = MessageFormatter.arrayFormat(this.getMsg(), args).getMessage();
        return new ValidationException(msg);
    }
}