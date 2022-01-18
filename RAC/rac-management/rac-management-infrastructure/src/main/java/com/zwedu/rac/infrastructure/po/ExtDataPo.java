package com.zwedu.rac.infrastructure.po;

import java.util.Date;

public class ExtDataPo {
    private Long id;

    private Long extPropertyId;

    private Long bizDataId;

    private Long bizLineId;

    private String value;

    private String remark;

    private Date createTime;

    private Long createUserId;

    private Date updateTime;

    private Long updateUserId;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExtPropertyId() {
        return extPropertyId;
    }

    public void setExtPropertyId(Long extPropertyId) {
        this.extPropertyId = extPropertyId;
    }

    public Long getBizDataId() {
        return bizDataId;
    }

    public void setBizDataId(Long bizDataId) {
        this.bizDataId = bizDataId;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}