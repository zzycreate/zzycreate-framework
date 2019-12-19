package com.zzycreate.zzz.utils;

import lombok.Data;

import java.util.List;

/**
 * 分页循环工具类
 *
 * @author zzycreate
 * @date 2018/10/9
 */
@Data
public abstract class BaseLoop<T> {

    private static final int DEFAULT_PAGE_SIZE = 1000;

    private List<T> list;
    private int pageSize = DEFAULT_PAGE_SIZE;

    public BaseLoop(List<T> list, int s) {
        this(list, s, true);
    }

    public BaseLoop(List<T> list, int s, boolean startImmediately) {
        this(list);
        this.pageSize = (s == 0) ? DEFAULT_PAGE_SIZE : s;
        if(startImmediately){
            this.process();
        }
    }

    public BaseLoop(List<T> list) {
        this.list = list;
    }

    public BaseLoop() {
    }

    /**
     * 待实现的分页处理逻辑
     *
     * @param rows     当前页数据
     * @param page     当前页页码
     * @param pageSize 每页数量
     * @param start    当前页起始索引
     * @param end      当前页结束索引
     */
    public abstract void loop(List<T> rows, int page, int pageSize, int start, int end);

    /**
     * 分页处理
     */
    public void process() {
        int size = this.list.size();
        // 逢一进一方式算页码 (size-1)/pageSize+1 ; 以下写法解决size为0时计算异常
        int pageNum = (size - 1 + this.pageSize) / this.pageSize;

        for (int i = 0; i < pageNum; i++) {
            int start = i * this.pageSize;
            int end = Math.min((i + 1) * this.pageSize, size);
            List<T> subList = this.list.subList(start, end);
            loop(subList, i, this.pageSize, start, end);
        }
    }

    public BaseLoop<T> list(List<T> list) {
        this.list = list;
        return this;
    }

    public BaseLoop<T> pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
