package com.cwzsmile.base;

import java.util.List;

/**
 * Created by csh9016 on 2019/6/27.
 */
public interface IBaseService<D,T> {

    T get(Long id);

    void insert(D record);

    void update(D record);

    void delete(D record);

    List<T> queryAll(D condition);

    PageResult<T> queryPage(D condition, PageParam pageParam);
}
