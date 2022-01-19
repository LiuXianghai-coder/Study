package com.zwedu.rac.sdk.common;

import java.util.List;

/**
 * 树形结构
 *
 * @author qingchuan
 * @date 2020/12/20
 */
public class TreeNode<T extends TreeNode> {
    /**
     * ID
     */
    private Long id;
    /**
     * 父节点ID
     */
    private Long parentId;
    /**
     * 树节点ID列表
     */
    private List<Long> pathIds;
    /**
     * 父节点ID
     */
    private String parentPath;
    /**
     * 是否有子节点
     */
    private Boolean leaf;
    /**
     * 是否有子节点
     */
    private Boolean hasChild;
    /**
     * 树层级
     */
    private Integer level;
    /**
     * 子节点列表
     */
    private List<T> childList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<T> getChildList() {
        return childList;
    }

    public void setChildList(List<T> childList) {
        this.childList = childList;
    }

    public List<Long> getPathIds() {
        return pathIds;
    }

    public void setPathIds(List<Long> pathIds) {
        this.pathIds = pathIds;
    }
}
