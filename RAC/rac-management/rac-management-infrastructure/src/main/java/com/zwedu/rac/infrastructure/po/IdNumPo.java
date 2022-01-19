package com.zwedu.rac.infrastructure.po;

/**
 * ID对应统计数
 *
 * @author qingchuan
 * @date 2020/12/19
 */
public class IdNumPo {
    /**
     * ID
     */
    private Long id;
    /**
     * 数量
     */
    private Long childCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChildCount() {
        return childCount;
    }

    public void setChildCount(Long childCount) {
        this.childCount = childCount;
    }
}
