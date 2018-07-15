package com.lz.design.common;

import java.util.List;

/**
 * @author Xingwu.Jia
 * @date 2018/5/19 22:44
 */
public class PageResponse<T> {
    /**
     * 当前页码数
     */
    private int currentPage;

    /**
     * 每页最大展示数
     */
    private int pageSize;
    /**
     * 展示总数
     */
    private long totalCount;
    /**
     * 展示数据
     */
    private List<T> list;

    public PageResponse() {
    }

    public PageResponse(int currentPage, int pageSize, long totalCount, List<T> list) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
