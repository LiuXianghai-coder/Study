package com.zwedu.rac.application.dto;


/**
 * 分页数据
 *
 * @author qingchuan
 * @date 2020/5/25
 */
public class ReqPaginationDto<T> {
    /**
     * ID
     */
    private Long id;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 检索值
     */
    private String searchVal;
    /**
     * 业务线ID
     */
    private Long bizLineId;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public String getSearchVal() {
        return searchVal;
    }

    public void setSearchVal(String searchVal) {
        this.searchVal = searchVal;
    }

    public Long getBizLineId() {
        return bizLineId;
    }

    public void setBizLineId(Long bizLineId) {
        this.bizLineId = bizLineId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
