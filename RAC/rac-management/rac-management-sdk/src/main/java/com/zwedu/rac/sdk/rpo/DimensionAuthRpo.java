package com.zwedu.rac.sdk.rpo;

import java.io.Serializable;

/**
 * 数据权限查询请求参数对象
 *
 * @author qingchuan
 * @date 2020/12/11
 */
public class DimensionAuthRpo implements Serializable {
    /**
     * 业务线ID
     */
    private Long bizLineId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 维度英文名
     */
    private String dimensionEnName;

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDimensionEnName() {
        return dimensionEnName;
    }

    public void setDimensionEnName(String dimensionEnName) {
        this.dimensionEnName = dimensionEnName;
    }
}
