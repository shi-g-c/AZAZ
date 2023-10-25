package com.azaz.model;

import java.io.Serializable;

/**
 * 分页查询结果返回类
 * @author shigc
 * &#064;date  2023/10/25
 */
public class PageResponseResult extends ResponseResult implements Serializable {
    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页大小
     */
    private Integer size;
    /**
     * 总数
     */
    private Integer total;

    public PageResponseResult(Integer currentPage, Integer size, Integer total) {
        this.currentPage = currentPage;
        this.size = size;
        this.total = total;
    }

    public PageResponseResult() {

    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
