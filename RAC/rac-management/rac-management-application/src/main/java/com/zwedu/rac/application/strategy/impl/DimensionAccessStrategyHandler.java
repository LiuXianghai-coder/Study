package com.zwedu.rac.application.strategy.impl;

import com.zwedu.rac.application.strategy.DataAccessStrategyHandler;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.strategy.entity.DimensionStrategyInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 访问控制策略<p>用户属性</p>表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Slf4j
public class DimensionAccessStrategyHandler implements DataAccessStrategyHandler<DimensionStrategyInfo> {

    @Override
    public boolean hasAuth(DimensionStrategyInfo strategyRdo, RacContext racContext) {
        return false;
    }

    @Override
    public AuthInfo getAuthInfo(DimensionStrategyInfo strategyRdo, RacContext racContext) {
        return null;
    }
}
