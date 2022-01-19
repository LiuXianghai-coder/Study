package com.zwedu.rac.domain.entity.base;

import com.zwedu.rac.domain.common.validator.BizAssert;

/**
 * 基础实体类
 *
 * @author qingchuan
 * @date 2020/12/17
 */
public abstract class CommonEntity extends AbstractEntity {
    /**
     * 业务线ID
     */
    private Long bizLineId;

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        BizAssert.PARAM_EMPTY_ERROR.notNull(bizLineId, "业务线ID");
        this.bizLineId = bizLineId;
    }
}
