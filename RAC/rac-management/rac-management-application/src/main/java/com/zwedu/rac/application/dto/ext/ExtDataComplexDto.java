package com.zwedu.rac.application.dto.ext;

/**
 * 扩展属性数据
 *
 * @author qingchuan
 * @date 2020/12/17
 */
public class ExtDataComplexDto {
    /**
     * 扩展属性ID
     */
    private Long extPropertyId;
    /**
     * 扩展属性名名称
     */
    private String extPropertyName;
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
     * 扩展属性名称
     */
    private String text;
    /**
     * 备注
     */
    private String remark;

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

    public String getExtPropertyName() {
        return extPropertyName;
    }

    public void setExtPropertyName(String extPropertyName) {
        this.extPropertyName = extPropertyName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
