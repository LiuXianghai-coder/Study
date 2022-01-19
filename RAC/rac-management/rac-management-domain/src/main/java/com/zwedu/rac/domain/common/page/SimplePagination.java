package com.zwedu.rac.domain.common.page;

/**
 * 简单分页
 *
 * @author qingchuan
 * @date 2020/12/12
 */
public class SimplePagination {
    /**
     * 默认分页大小
     */
    public static final int DEF_PAGE_SIZE = 20;
    /**
     * 每页大小
     */
    protected int pageSize = 20;
    /**
     * 当前页码
     */
    protected int pageNo = 1;

    /**
     * if pageSize< 1 then pageSize=DEF_COUNT
     *
     * @param pageSize 每页数量
     */
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            this.pageSize = DEF_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    /**
     * if pageNo < 1 then pageNo=1
     *
     * @param pageNo 当前页码
     */
    public void setPageNo(int pageNo) {
        if (pageNo < 1) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo;
        }
    }
}
