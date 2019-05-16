package com.memory.common.utils;

/**
 * @author INS6+
 * @date 2019/5/8 18:22
 */

public class PageResult {
    //当前页码数
    private Integer pageNumber;
    //每页显示数量
    private Integer pageSize;
    //一共分几页
    private Integer totalPages;
    //当前分页(记录)偏移量
    private Long offset;
    //查询结果总行数
    private Long totalElements;

    //返回list数据
    private Object data;

    public PageResult(){};

    public PageResult(Integer pageNumber, Integer pageSize, Integer totalPages, Long offset,Long totalElements, Object data){
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.offset = offset;
        this.totalElements = totalElements;
        this.data = data;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
