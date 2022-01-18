package com.zwedu.rac.domain.common.strategy.entity;

import com.zwedu.rac.domain.common.enums.StrategyTypeEnum;
import lombok.Data;

/**
 * 用户属性策略信息
 *
 * @author qingchuan
 * @date 2021/1/3
 */
@Data
public class UserPropertyStrategyInfo extends StrategyInfo {
    /**
     * constructor
     */
    public UserPropertyStrategyInfo() {
        this.setType(StrategyTypeEnum.USER_PROPERTY.getText());
    }

    /**
     * 获取用户属性类型
     */
    private String propertyType;
    /**
     * 匹配对应的属性名称
     */
    private String propertyName;
    /**
     * 匹配对应的属性名称
     */
    private String entityEnName;
    /**
     * 匹配对应的属性名称
     */
    private String entityPropertyType;
    /**
     * 匹配对应的属性名称
     */
    private String entityPropertyName;

    public UserPropertyStrategyInfo propertyType(String propertyType) {
        this.propertyType = propertyType;
        return this;
    }


    public UserPropertyStrategyInfo propertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public UserPropertyStrategyInfo entityEnName(String entityEnName) {
        this.entityEnName = entityEnName;
        return this;
    }

    public UserPropertyStrategyInfo entityPropertyType(String entityPropertyType) {
        this.entityPropertyType = entityPropertyType;
        return this;
    }

    public UserPropertyStrategyInfo entityPropertyName(String entityPropertyName) {
        this.entityPropertyName = entityPropertyName;
        return this;
    }
}
