package com.zwedu.rac.infrastructure.po;

import java.util.Date;

public class DimensionNodeControlPo {
    private Long id;

    private Long subjectNodeId;

    private Long objectNodeId;

    private Date createTime;

    private Long createUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectNodeId() {
        return subjectNodeId;
    }

    public void setSubjectNodeId(Long subjectNodeId) {
        this.subjectNodeId = subjectNodeId;
    }

    public Long getObjectNodeId() {
        return objectNodeId;
    }

    public void setObjectNodeId(Long objectNodeId) {
        this.objectNodeId = objectNodeId;
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