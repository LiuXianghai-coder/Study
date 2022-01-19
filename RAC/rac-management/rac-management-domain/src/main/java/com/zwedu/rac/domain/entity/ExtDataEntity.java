package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.CommonEntity;

/**
 * 扩展属性值实体类
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class ExtDataEntity extends CommonEntity {
    /**
     * 扩展属性ID
     */
    private Long extPropertyId;
    /**
     * 业务数据ID
     */
    private Long bizDataId;
    /**
     * 扩展属性名
     */
    private String name;
    /**
     * 扩展属性值
     */
    private String value;

    @Override
    public void validate() {
    }

    @Override
    public void create(Long currentUserId) {
        validate();
        completeForCreate(this, currentUserId);
    }


    @Override
    public void edit(Long currentUserId) {
        validate();
        completeForUpdate(this, currentUserId);
    }


    @Override
    public void delete(Long currentUserId) {
        validate();
        completeForDelete(this, currentUserId);
    }

    public Long getExtPropertyId() {
        return extPropertyId;
    }

    public void setExtPropertyId(Long extPropertyId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(extPropertyId, "属性ID");
        this.extPropertyId = extPropertyId;
    }

    public Long getBizDataId() {
        return bizDataId;
    }

    public void setBizDataId(Long bizDataId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(bizDataId, "业务数据ID");
        this.bizDataId = bizDataId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(value, "属性值");
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 新增扩展值
     *
     * @param currentUserId 当前用户
     */
    public void addExtValue(Long currentUserId) {
        create(currentUserId);
    }
}
