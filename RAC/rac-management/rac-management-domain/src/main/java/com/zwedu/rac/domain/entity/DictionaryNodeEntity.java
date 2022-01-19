package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.TreeEntity;

/**
 * 数据字典节点实体类
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class DictionaryNodeEntity extends TreeEntity {

    /**
     * 值
     */
    private String value;
    /**
     * 字典ID
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

    /**
     * 新增逻辑 校验和填充默认值
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void create(Long currentUserId, DictionaryNodeEntity parentNode) {
        create(currentUserId);
        completePath(parentNode);
    }


    /**
     * 修改节点逻辑填充默认值和修改父节点逻辑处理
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void edit(Long currentUserId, DictionaryNodeEntity parentNode) {
        edit(currentUserId);
        completePath(parentNode);
    }


    @Override
    public void edit(Long currentUserId) {
        validate();
        BizAssert.LOOP_TREE_ERROR.notTrue(this.getId().longValue() == this.getParentId().longValue());
        completeForUpdate(this, currentUserId);
    }

    @Override
    public void delete(Long currentUserId) {
        validate();
        completeForDelete(this, currentUserId);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(value, "节点值");
        this.value = value;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(dictionaryId, "字典ID");
        this.dictionaryId = dictionaryId;
    }
}
