package com.zwedu.rac.interfaces.common;

import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.exception.BaseException;
import com.zwedu.rac.domain.common.util.BaseResponse;
import com.zwedu.rac.domain.common.util.Jackson;
import com.zwedu.rac.domain.common.util.ResponseUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义拦截器-方法拦截器，基于spring aop
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Slf4j
@Aspect
@Component
public class HttpResponseInterceptor {

    @Around("@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    public Object handleResponse(ProceedingJoinPoint pjp) {
        LogRecord buildLog = buildLog(pjp);
        try {
            Object result = pjp.proceed();
            log.info("invoke {}.{}, param:{}",
                    buildLog.clazz, buildLog.method, Jackson.toJson(buildLog.getParamList()));
            return result;
        } catch (BaseException ex) {
            log.error("invoke {}.{}, param:{}, ex-code:{}, ex-msg={}, {}",
                    buildLog.clazz, buildLog.method, Jackson.toJson(buildLog.getParamList()),
                    ex.getCode(), ex.getMsg(), ex);
            BaseResponse responseData = ResponseUtil.fail(ex.getCode(), ex.getMsg());
            return responseData;
        } catch (Throwable ex) {
            log.error("invoke {}.{}, param:{}, ex-msg={}, {}",
                    buildLog.clazz, buildLog.method, Jackson.toJson(buildLog.getParamList()),
                    ex.getMessage(), ex);
            BaseResponse responseData = ResponseUtil.fail("系统异常，请稍后再试");
            return responseData;
        }
    }

    /**
     * 构建日志对象
     *
     * @param pjp 拦截点
     * @return 日志对象
     */
    private LogRecord buildLog(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Object[] paramValues = pjp.getArgs();
        Class<?> clazz = pjp.getTarget().getClass();
        String[] paramNames = methodSignature.getParameterNames();
        List<String> kvList = Lists.newArrayList();
        if (paramNames != null) {
            for (int i = 0; i < paramNames.length; i++) {
                String paramName = paramNames[i];
                Object paramValue = paramValues[i];
                kvList.add(paramName + ":" + Jackson.toJson(paramValue));
            }
        }
        return LogRecord.builder().clazz(clazz.getName()).method(methodSignature.getName())
                .paramList(kvList).build();
    }

    @Getter
    @Builder
    public static class LogRecord {
        /**
         * 类名
         */
        private String clazz;
        /**
         * 方法名
         */
        private String method;
        /**
         * 参数
         */
        private List<String> paramList;
    }
}