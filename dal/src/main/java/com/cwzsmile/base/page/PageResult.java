package com.cwzsmile.base.page;

import lombok.Data;

import java.util.List;

/**
 * Created by csh9016 on 2019/6/28.
 */
@Data
public class PageResult<T> extends PageParam {

    private Long total;

    private List<T> list;

    public static <T> PageResult<T> newInstance(List list) {
        PageResult<T> page = new PageResult<>();
        page.setList(list);
        return page;
    }
}
