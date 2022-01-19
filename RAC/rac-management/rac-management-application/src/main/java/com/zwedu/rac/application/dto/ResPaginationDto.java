package com.zwedu.rac.application.dto;

import lombok.Data;

import java.util.List;

/**
 * 分页数据
 *
 * @author qingchuan
 * @date 2020/5/25
 */
public class ResPaginationDto<T> {
    /**
     * 总数量
     */
    private long totalCount = 0;
    /**
     * 当前页的数据
     */
    private List<T> dataList;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
