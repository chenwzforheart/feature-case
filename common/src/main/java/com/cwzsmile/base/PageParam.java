package com.cwzsmile.base;

import lombok.Data;

/**
 * Created by csh9016 on 2019/6/28.
 */
@Data
public class PageParam {

    private Integer size;

    private Integer page;

    private String sort;

    public PageParam() {
    }

    public PageParam(Integer size, Integer page) {
        this.size = size;
        this.page = page;
    }

    public static PageParam newInstance(Integer size, Integer page) {
        return new PageParam(size, page);
    }
}
