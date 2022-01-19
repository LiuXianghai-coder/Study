package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.enums.UseExtPropertyEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.BaseEntity;

/**
 * 数据字典实体类
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class DictionaryEntity extends BaseEntity {

    /**
     * 是否使用扩展属性
     */
    private Integer useExtProperty;

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

    public Integer getUseExtProperty() {
        return useExtProperty;
    }

    public void setUseExtProperty(Integer useExtProperty) {
        BizAssert.EXISTED_VALUE_ERROR.isTrue(useExtProperty != null &&
                UseExtPropertyEnum.hasEnum(useExtProperty));
        this.useExtProperty = useExtProperty;
    }
}
