package org.tango.core.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author tAngo
 */
public class Pager<T> implements Serializable {

    private int page = 1;
    private int rows = 0;
    private int pageSize = 10;
    private boolean doPage;
    private List<T> dataList;

    public Pager() {
        super();
    }

    public Pager(boolean doPage) {
        super();
        this.doPage = doPage;
    }

    public Pager(int page, int pageSize, boolean doPage) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.doPage = doPage;
    }

    /**
     * ************************************************* [ Getter & Setter ] ***************************************************
     */
    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getStart() {
        return (this.page - 1) * this.getPageSize();
    }

    public int getLimit() {
        return this.page * this.getPageSize();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        if (this.rows % this.pageSize == 0) {
            return this.rows / this.pageSize;
        }
        return this.rows / this.pageSize + 1;
    }

    public boolean isDoPage() {
        return doPage;
    }

    public void setDoPage(boolean doPage) {
        this.doPage = doPage;
    }
}
