package com.zwedu.rac.infrastructure.po;

import java.util.Date;

public class UserDimensionNodePo {
    private Long id;

    private Long userId;

    private Long dimensionNodeId;

    private Date createTime;

    private Long createUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDimensionNodeId() {
        return dimensionNodeId;
    }

    public void setDimensionNodeId(Long dimensionNodeId) {
        this.dimensionNodeId = dimensionNodeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}