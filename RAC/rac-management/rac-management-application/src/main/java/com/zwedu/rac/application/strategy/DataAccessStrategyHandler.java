package com.zwedu.rac.application.strategy;

import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.strategy.entity.StrategyInfo;

/**
 * 策略表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
public interface DataAccessStrategyHandler<T extends StrategyInfo> {
    /**
     * 是否有权限操作
     *
     * @param strategyRdo 策略对象
     * @param racContext  上下文
     * @return true or false
     */
    boolean hasAuth(T strategyRdo, RacContext racContext);

    /**
     * 策略表达式处理方法
     *
     * @param strategyRdo 策略对象
     * @param racContext  上下文
     * @return 权限信息
     */
    AuthInfo getAuthInfo(T strategyRdo, RacContext racContext);
}
