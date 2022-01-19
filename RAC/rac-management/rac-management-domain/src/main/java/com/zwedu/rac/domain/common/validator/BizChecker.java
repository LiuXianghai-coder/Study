package com.zwedu.rac.domain.common.validator;


import com.zwedu.rac.domain.common.exception.BizException;
import org.slf4j.helpers.MessageFormatter;


/***
 * 领域校验断言器
 *
 * @author qingchuan
 * @date 2020/5/13
 */
public interface BizChecker extends IChecker {

    @Override
    default BizException newException(Object... args) {
        String msg = MessageFormatter.arrayFormat(this.getMsg(), args).getMessage();
        return new BizException(this.getCode(), msg);
    }
}