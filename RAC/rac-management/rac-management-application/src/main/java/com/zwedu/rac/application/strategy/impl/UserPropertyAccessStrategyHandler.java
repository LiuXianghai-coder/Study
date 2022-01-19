package com.zwedu.rac.application.strategy.impl;

import com.zwedu.rac.application.strategy.DataAccessStrategyHandler;
import com.zwedu.rac.application.strategy.DataAccessStrategyHandlerBuilder;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.enums.StrategyTypeEnum;
import com.zwedu.rac.domain.common.strategy.entity.UserPropertyStrategyInfo;
import com.zwedu.rac.domain.common.validator.BizAssert;
import lombok.extern.slf4j.Slf4j;

/**
 * 访问控制策略<p>用户属性</p>表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Slf4j
public class UserPropertyAccessStrategyHandler implements DataAccessStrategyHandler<UserPropertyStrategyInfo> {

    @Override
    public boolean hasAuth(UserPropertyStrategyInfo strategyInfo, RacContext racContext) {
        return DataAccessStrategyHandlerBuilder.instance(StrategyTypeEnum.USER_PROPERTY.getText(),
                strategyInfo.getPropertyType()).hasAuth(strategyInfo, racContext);
    }

    @Override
    public AuthInfo getAuthInfo(UserPropertyStrategyInfo strategyInfo, RacContext racContext) {
        BizAssert.PARAM_EMPTY_ERROR.allNotNull(strategyInfo, racContext.getUserRdo());
        return DataAccessStrategyHandlerBuilder.instance(StrategyTypeEnum.USER_PROPERTY.getText(),
                strategyInfo.getPropertyType()).getAuthInfo(strategyInfo, racContext);
    }
}
