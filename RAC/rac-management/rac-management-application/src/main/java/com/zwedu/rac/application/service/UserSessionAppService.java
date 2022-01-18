package com.zwedu.rac.application.service;

import com.google.common.collect.Maps;
import com.zwedu.rac.application.UserSession;
import com.zwedu.rac.application.dto.user.UserSimpleDto;
import com.zwedu.rac.domain.common.validator.ParamAssert;
import com.zwedu.rac.sdk.rdo.FuncRdo;
import com.zwedu.rac.sdk.rdo.StrategyRdo;
import com.zwedu.rac.sdk.rdo.UserRdo;
import com.zwedu.rac.sdk.rpo.FuncAuthRpo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户session管理
 *
 * @author qingchuan
 * @date 2020/12/25
 */
@Service
public class UserSessionAppService {
    @Resource
    private AuthAppService authAppService;

    @Resource
    private UserAppService userAppService;

    /**
     * 根据UserId获取session信息
     *
     * @param bizLineId 业务线ID
     * @param userId    用户ID
     * @return session信息
     */
    public UserSession getSession(Long bizLineId, String userName) {
        ParamAssert.PARAM_EMPTY_ERROR.notNull(userName);
        UserSimpleDto userSimpleDto = userAppService.queryByEnName(userName);
        // 获取用户信息
        UserRdo userRdo = authAppService.queryUser(bizLineId, userSimpleDto.getId());
        // 获取功能权限策略
        List<FuncRdo> funcRdoList = authAppService.listFuncAuth(new FuncAuthRpo(bizLineId, userSimpleDto.getId()));
        Map<String, StrategyRdo> funcStrategyMap = Maps.newHashMap();
        for (FuncRdo funcRdo : funcRdoList) {
            for (String url : funcRdo.getUrlSet()) {
                funcStrategyMap.put(url, funcRdo.getStrategyRdo());
            }
        }
        return UserSession.builder().userRdo(userRdo).funcStrategyMap(funcStrategyMap).build();
    }
}
