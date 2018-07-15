package com.lz.design.common;

import java.io.Serializable;

/**
 * @author Xingwu.Jia
 * @date 2018/5/19 22:44
 */
public class PageRequest implements Serializable {
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    /**
     * 当前页码数
     */
    private int currentPage;
    /**
     * 每页最大展示数
     */

    private int pageSize = DEFAULT_PAGE_SIZE;

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
}
