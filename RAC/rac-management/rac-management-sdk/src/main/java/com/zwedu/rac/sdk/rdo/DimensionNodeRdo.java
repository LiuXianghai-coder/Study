package com.zwedu.rac.sdk.rdo;

import java.io.Serializable;

/**
 * 维度节点
 *
 * @author qingchuan
 * @date 2020/12/23
 */
public class DimensionNodeRdo implements Serializable {
    /**
     * 维度节点ID
     */
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
