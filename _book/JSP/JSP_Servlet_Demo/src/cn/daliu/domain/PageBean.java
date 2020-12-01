package cn.daliu.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount; // 总记录数: select count(*) from user
    // 总页码: 总页数 = 总记录数 % 每页显示条数(rows) == 0 ? 总记录数 % 每页显示条数(rows) : 总记录数 % 每页显示条数(rows) +1
    private int totalPage;
    // 每页的数据: select * from user limit 0(开始索引), 5(查询的条数)
    /**
     第1页: (0, 5)
     第2页: (5, 5)
     第3页: (10, 5)
     ...
     开始索引 = (currentPage - 1) * rows(即每页显示条数)
     */
    private List<T> list ;
    private int currentPage ; // 当前页码, 由client传过来
    private int rows; // 每页显示的记录数

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", list=" + list +
                ", currentPage=" + currentPage +
                ", rows=" + rows +
                '}';
    }
}