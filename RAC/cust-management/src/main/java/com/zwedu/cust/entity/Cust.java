package com.zwedu.cust.entity;

/**
 * 客户管理
 *
 * @author qingchuan
 * @date 2020/12/26
 */
public class Cust {
    /**
     * 客户ID
     */
    private Long id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 客服岗位ID
     */
    private String posId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosId() {
        return posId;
    }

    public void setPosId(String posId) {
        this.posId = posId;
    }
}
