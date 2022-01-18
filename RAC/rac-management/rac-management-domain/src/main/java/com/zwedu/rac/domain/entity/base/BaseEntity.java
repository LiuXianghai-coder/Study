package com.zwedu.rac.domain.entity.base;

import com.zwedu.rac.domain.common.validator.BizAssert;

import static com.zwedu.rac.domain.common.constant.SystemConstant.NORMAL_COLUMN_LENGTH;

/**
 * 基础实体类
 *
 * @author qingchuan
 * @date 2020/12/17
 */
public abstract class BaseEntity extends AbstractEntity {
    /**
     * 英文名
     */
    private String enName;
    /**
     * 中文名
     */
    private String cnName;
    /**
     * 业务线ID
     */
    private Long bizLineId;


    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(enName, "用户英文名");
        BizAssert.PARAM_EMPTY_ERROR.isTrue(enName.length() < NORMAL_COLUMN_LENGTH, "用户英文名");
        this.enName = enName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(cnName, "用户中文名");
        BizAssert.PARAM_EMPTY_ERROR.isTrue(cnName.length() < NORMAL_COLUMN_LENGTH, "用户中文名");
        this.cnName = cnName;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, "业务线ID");
        this.bizLineId = bizLineId;
    }
}
