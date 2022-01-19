package com.zwedu.rac.application.dto.ext;

/**
 * 扩展属性数据
 *
 * @author qingchuan
 * @date 2020/12/17
 */
public class ExtDataSimpleDto {
    /**
     * 扩展属性ID
     */
    private Long extPropertyId;
    /**
     * 业务数据ID
     */
    private Long bizDataId;
    /**
     * 业务线ID
     */
    private Long bizLineId;
    /**
     * 扩展属性值
     */
    private String value;
    /**
     * 备注
     */
    private String remark;
    /**
     * 分级管控ID
     */
    private Long decentralizedControlId;

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
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getDecentralizedControlId() {
        return decentralizedControlId;
    }

    public void setDecentralizedControlId(Long decentralizedControlId) {
        this.decentralizedControlId = decentralizedControlId;
    }
}
