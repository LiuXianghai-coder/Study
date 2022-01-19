package com.zwedu.rac.domain.common.annotation;

import java.lang.annotation.*;

/**
 * 数据权限实现的注解
 *
 * @author qingchuan
 * @date 2020/12/21
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WriteAuth {
    /**
     * 无权时的提示
     */
    String msg() default "";
}