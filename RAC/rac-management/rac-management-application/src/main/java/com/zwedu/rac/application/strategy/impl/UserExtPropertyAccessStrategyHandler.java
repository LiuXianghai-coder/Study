package com.zwedu.rac.application.strategy.impl;

import com.zwedu.rac.application.strategy.DataAccessStrategyHandler;
import com.zwedu.rac.domain.common.enums.DataAccessFieldEnum;
import com.zwedu.rac.domain.common.strategy.entity.AuthInfo;
import com.zwedu.rac.application.strategy.vo.RacContext;
import com.zwedu.rac.domain.common.constant.SeparationChar;
import com.zwedu.rac.domain.common.enums.DataAccessEnum;
import com.zwedu.rac.domain.common.strategy.entity.UserPropertyStrategyInfo;
import com.zwedu.rac.domain.common.util.Collections2;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.sdk.rdo.UserRdo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 访问控制策略<p>用户属性</p>表达式处理器
 *
 * @author qingchuan
 * @date 2020/12/24
 */
@Slf4j
public class UserExtPropertyAccessStrategyHandler
        implements DataAccessStrategyHandler<UserPropertyStrategyInfo> {
    @Override
    public boolean hasAuth(UserPropertyStrategyInfo strategyInfo, RacContext racContext) {
        AuthInfo authInfo = getAuthInfo(strategyInfo, racContext);
        if (CollectionUtils.isNotEmpty(authInfo.getAuthList())
                && MapUtils.isNotEmpty(racContext.getParamMap())
                && racContext.getParamMap().containsKey(strategyInfo.getEntityPropertyName())
                && authInfo.getAuthList().contains(ObjectUtils.toString(racContext.getParamMap()
                .get(strategyInfo.getEntityPropertyName()), () -> StringUtils.EMPTY))) {
            return true;
        }
        return false;
    }

    @Override
    public AuthInfo getAuthInfo(UserPropertyStrategyInfo strategyInfo, RacContext racContext) {
        BizAssert.PARAM_EMPTY_ERROR.allNotNull(strategyInfo, racContext.getUserRdo());
        UserRdo userRdo = racContext.getUserRdo();
        // 扩展属性的话，从扩展属性数据中获取
        if (MapUtils.isNotEmpty(userRdo.getExtData())
                && userRdo.getExtData().containsKey(strategyInfo.getPropertyName())) {
            String value = ObjectUtils.toString(userRdo.getExtData()
                    .get(strategyInfo.getPropertyName()), () -> StringUtils.EMPTY);
            if (StringUtils.isEmpty(value)) {
                log.error("not set user ext property={} value", strategyInfo.getPropertyName());
                return AuthInfo.builder().dataAccess(DataAccessEnum.NONE).build();
            }
            return AuthInfo.builder().dataAccess(DataAccessEnum.DECENTRALIZED)
                    .fieldName(strategyInfo.getEntityPropertyName())
                    .dbFieldName(DataAccessFieldEnum.of(strategyInfo.getEntityPropertyName()).getText())
                    .authList(Collections2.toStringList(value, SeparationChar.COMMA)).build();
        }
        return AuthInfo.builder().dataAccess(DataAccessEnum.NONE).build();
    }
}
