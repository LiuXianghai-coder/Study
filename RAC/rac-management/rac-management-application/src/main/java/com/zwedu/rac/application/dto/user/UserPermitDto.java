package com.zwedu.rac.application.dto.user;

import java.util.List;

/**
 * 用户权限DTO
 *
 * @author qingchuan
 * @date 2020/12/16
 */
public class UserPermitDto {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 业务线ID
     */
    private Long bizLineId;
    /**
     * 角色列表
     */
    private List<Long> roleIds;
    /**
     * 维度节点列表
     */
    private List<Long> dimensionNodeIds;
    /**
     * 分级管控ID
     */
    private Long decentralizedControlId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getDimensionNodeIds() {
        return dimensionNodeIds;
    }

    public void setDimensionNodeIds(List<Long> dimensionNodeIds) {
        this.dimensionNodeIds = dimensionNodeIds;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public Long getDecentralizedControlId() {
        return decentralizedControlId;
    }

    public void setDecentralizedControlId(Long decentralizedControlId) {
        this.decentralizedControlId = decentralizedControlId;
    }
}
