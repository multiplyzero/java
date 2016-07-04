package com.yiwugou.reb.api.utils;

import java.util.List;

import lombok.ToString;

/**
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月2日 下午2:27:41
 */
@ToString
public class Pager<T> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> datas;
    private int currPage;
    private int pageSize;
    private int firstResult;
    private int total;
    private int totalPage;

    public static final int DEFAULT_PAGE_SIZE = 20;

    public Pager(Integer currPage, Integer pageSize) {
        if (currPage == null || currPage <= 1) {
            this.currPage = 1;
        } else {
            this.currPage = currPage;
        }

        if (pageSize == null || pageSize <= 1 || pageSize > 1000) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }

        this.initFirstResult();
    }

    public Pager(Integer currPage) {
        this(currPage, DEFAULT_PAGE_SIZE);
    }

    private void initFirstResult() {
        this.firstResult = (this.currPage - 1) * this.pageSize;
    }

    private void initTotalPage() {
        if (this.total == 0) {
            this.totalPage = 0;
        } else {
            this.totalPage = (this.total + this.pageSize - 1) / this.pageSize;
        }
        this.processCurrPage();
    }

    private void processCurrPage() {
        if (this.currPage > this.totalPage) {
            this.currPage = this.totalPage;
        }
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.initTotalPage();
    }

    public int getFirstResult() {
        return firstResult;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

}
