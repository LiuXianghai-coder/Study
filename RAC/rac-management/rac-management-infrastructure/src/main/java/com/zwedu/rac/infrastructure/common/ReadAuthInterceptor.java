package com.zwedu.rac.infrastructure.common;

import com.zwedu.rac.domain.common.annotation.ReadAuth;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.Properties;

/**
 * mybatis数据权限拦截器
 *
 * @author qingchuan
 * @date 2020/12/26
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
@Slf4j
public class ReadAuthInterceptor implements Interceptor {

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取方法上的数据权限注解，如果没有注解，则直接通过
        ReadAuth readAuth = getPermissionByDelegate(mappedStatement);
        if (readAuth != null) {
            log.info(readAuth.alias());
        }
        return invocation.proceed();
    }


    /**
     * 获取数据权限注解信息
     *
     * @param statement 清单信息
     * @return 读权限注解
     */
    private ReadAuth getPermissionByDelegate(MappedStatement statement) throws ClassNotFoundException {
        ReadAuth readAuth = null;
        String id = statement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
        final Class<?> cls = Class.forName(className);
        final Method[] method = cls.getMethods();
        for (Method me : method) {
            if (me.getName().equals(methodName) && me.isAnnotationPresent(ReadAuth.class)) {
                readAuth = me.getAnnotation(ReadAuth.class);
            }
        }
        return readAuth;
    }

}
