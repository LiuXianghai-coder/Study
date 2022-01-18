package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.enums.ExtPropertyTypeEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.BaseEntity;

/**
 * 扩展属性实体类
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class ExtPropertyEntity extends BaseEntity {
    /**
     * 业务实体ID
     */
    private Long bizEntityId;
    /**
     * 扩展属性类型 {@link ExtPropertyTypeEnum}
     */
    private Integer type;
    /**
     * 业务实体ID
     */
    private String defaultValue;
    /**
     * 业务实体ID
     */
    private Long dictionaryId;

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


    public Long getBizEntityId() {
        return bizEntityId;
    }

    public void setBizEntityId(Long bizEntityId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(bizEntityId, "业务实体ID");
        this.bizEntityId = bizEntityId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(bizEntityId, "扩展属性类型");
        BizAssert.INCORRECT_VALUE_ERROR.isTrue(ExtPropertyTypeEnum.hasEnum(type), "扩展属性类型");
        this.type = type;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
