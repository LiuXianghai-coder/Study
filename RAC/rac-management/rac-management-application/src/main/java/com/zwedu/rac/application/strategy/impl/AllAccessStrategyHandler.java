package com.zwedu.rac.application.strategy.impl;

import com.zwedu.rac.application.strategy.DataAccessStrategyHandler;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.enums.DataAccessEnum;
import com.zwedu.rac.domain.common.strategy.entity.AllStrategyInfo;

/**
 * 访问控制策略<p>全部</p>表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
public class AllAccessStrategyHandler implements DataAccessStrategyHandler<AllStrategyInfo> {

    @Override
    public boolean hasAuth(AllStrategyInfo strategyRdo, RacContext racContext) {
        return true;
    }

    @Override
    public AuthInfo getAuthInfo(AllStrategyInfo strategyRdo, RacContext racContext) {
        return AuthInfo.builder().dataAccess(DataAccessEnum.ALL).build();
    }
}
