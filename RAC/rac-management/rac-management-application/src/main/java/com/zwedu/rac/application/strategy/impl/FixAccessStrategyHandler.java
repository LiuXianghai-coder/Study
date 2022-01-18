package com.zwedu.rac.application.strategy.impl;

import com.zwedu.rac.application.strategy.DataAccessStrategyHandler;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.enums.DataAccessEnum;
import com.zwedu.rac.domain.common.strategy.entity.FixStrategyInfo;
import com.zwedu.rac.domain.common.validator.BizAssert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 访问控制策略<p>固定值</p>表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Slf4j
public class FixAccessStrategyHandler implements DataAccessStrategyHandler<FixStrategyInfo> {


    @Override
    public boolean hasAuth(FixStrategyInfo strategyRdo, RacContext racContext) {
        return false;
    }

    @Override
    public AuthInfo getAuthInfo(FixStrategyInfo strategyRdo, RacContext racContext) {
        BizAssert.PARAM_EMPTY_ERROR.allNotNull(strategyRdo);
        // 扩展属性的话，从扩展属性数据中获取
        if (CollectionUtils.isNotEmpty(strategyRdo.getAuthList())) {
            return AuthInfo.builder().dataAccess(DataAccessEnum.DECENTRALIZED)
                    .authList(strategyRdo.getAuthList()).build();
        }
        return AuthInfo.builder().dataAccess(DataAccessEnum.NONE).build();
    }
}
