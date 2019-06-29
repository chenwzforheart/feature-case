package com.cwzsmile.base.mybatis;

import com.cwzsmile.base.IBaseService;
import com.cwzsmile.base.PageParam;
import com.cwzsmile.base.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by csh9016 on 2019/6/27.
 */
public abstract class AbstractBaseService<D, M extends BaseMapper, T> implements IBaseService<D, T> {

    @Autowired
    private M innerMapper;

    private Supplier<T> supplier;

    public AbstractBaseService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get(Long id) {
        return (T) innerMapper.selectOne(id);
    }

    @Override
    public void insert(D record) {
        T t = supplier.get();
        BeanUtils.copyProperties(record, t);
        innerMapper.insert(t);
    }

    @Override
    public void update(D record) {
        T t = supplier.get();
        BeanUtils.copyProperties(record, t);
        innerMapper.updateByPrimaryKey(record);
    }

    @Override
    public void delete(D record) {
        innerMapper.deleteByPrimaryKey(record);
    }

    @Override
    public List<T> queryAll(D condition) {
        T t = supplier.get();
        BeanUtils.copyProperties(condition, t);
        return innerMapper.select(condition);
    }

    @Override
    public PageResult<T> queryPage(D condition, PageParam pageParam) {
        T t = supplier.get();
        BeanUtils.copyProperties(condition, t);
        return PageResult.newInstance(innerMapper.selectByRowBounds(t, MybatisUtil.toRowBounds(pageParam)));
    }
}
