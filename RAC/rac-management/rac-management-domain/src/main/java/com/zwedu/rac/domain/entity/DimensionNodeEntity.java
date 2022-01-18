package com.zwedu.rac.domain.entity;

import com.zwedu.rac.domain.common.enums.DimensionNodeEnum;
import com.zwedu.rac.domain.common.validator.BizAssert;
import com.zwedu.rac.domain.entity.base.TreeEntity;

/**
 * 维度节点实体类
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class DimensionNodeEntity extends TreeEntity {
    /**
     * 值
     */
    private Integer type;
    /**
     * 维度ID
     */
    private Long dimensionId;
    /**
     * 生效状态 {@link DimensionNodeEnum}
     */
    private Integer status;
    /**
     * 分级管控ID
     */
    private Long decentralizedControlId;

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
    public void create(Long currentUserId, DimensionNodeEntity parentNode) {
        create(currentUserId);
        completePath(parentNode);
    }


    /**
     * 修改节点逻辑填充默认值和修改父节点逻辑处理
     *
     * @param currentUserId 登录用户ID
     * @param parentNode    父节点信息
     */
    public void edit(Long currentUserId, DimensionNodeEntity parentNode) {
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(type, "维度节点类型");
        this.type = type;
    }

    public Long getDimensionId() {
        return dimensionId;
    }

    public void setDimensionId(Long dimensionId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(dimensionId, "维度ID");
        this.dimensionId = dimensionId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(status, "维度节点状态");
        BizAssert.INCORRECT_VALUE_ERROR.isTrue(DimensionNodeEnum.hasEnum(status), "维度节点状态");
        this.status = status;
    }

    public Long getDecentralizedControlId() {
        return decentralizedControlId;
    }

    public void setDecentralizedControlId(Long decentralizedControlId) {
        this.decentralizedControlId = decentralizedControlId;
    }
}
