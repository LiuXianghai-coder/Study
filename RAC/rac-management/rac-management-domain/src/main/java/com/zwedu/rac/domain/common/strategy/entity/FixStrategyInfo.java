package com.zwedu.rac.domain.common.strategy.entity;

import com.google.common.collect.Lists;
import com.zwedu.rac.domain.common.enums.StrategyTypeEnum;

import java.util.List;

/**
 * 固定值属性信息
 *
 * @author qingchuan
 * @date 2021/1/3
 */
public class FixStrategyInfo extends StrategyInfo {
    /**
     * 授权列表
     */
    private List<String> authList;

    /**
     * constructor
     */
    public FixStrategyInfo() {
        this.setType(StrategyTypeEnum.FIX.getText());
        this.authList = Lists.newArrayList();
    }

    /**
     * constructor
     */
    public FixStrategyInfo(List<String> authList) {
        this.setType(StrategyTypeEnum.FIX.getText());
        this.authList = authList;
    }

    public List<String> getAuthList() {
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }
}
