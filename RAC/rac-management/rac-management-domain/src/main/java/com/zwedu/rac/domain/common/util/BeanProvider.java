package com.zwedu.rac.domain.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 类BeanProvider.java的实现描述
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Component
public class BeanProvider implements ApplicationContextAware {

    /**
     * 应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 设定applicationContext
     * @param ac applicationContext
     * @throws BeansException beans异常
     */
    @Override
    public void setApplicationContext (ApplicationContext ac) throws BeansException {
        applicationContext = ac;
    }

    /**
     * 根据beanId获取bean对应的实例对象
     * @param beanName beanID
     * @return bean对应的实例
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
}
