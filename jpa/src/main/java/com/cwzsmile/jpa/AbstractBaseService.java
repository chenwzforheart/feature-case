package com.cwzsmile.jpa;

import com.cwzsmile.base.IBaseService;
import com.cwzsmile.base.PageParam;
import com.cwzsmile.base.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Supplier;

/**
 * Created by csh9016 on 2019/6/28.
 */
public abstract class AbstractBaseService<D, R extends JpaRepository, T> implements IBaseService<D, T> {

    @Autowired
    private R innerRepository;

    private Supplier<T> supplier;

    public AbstractBaseService(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get(Long id) {
        return (T) innerRepository.getOne(id);
    }

    @Override
    public void insert(D record) {
        T t = supplier.get();
        BeanUtils.copyProperties(record, t);
        innerRepository.saveAndFlush(t);
    }

    @Override
    public void update(D record) {
        T t = supplier.get();
        BeanUtils.copyProperties(record, t);
        innerRepository.save(t);
        innerRepository.flush();
    }

    @Override
    public void delete(D record) {
        T t = supplier.get();
        BeanUtils.copyProperties(record, t);
        innerRepository.deleteById(t);
    }

    @Override
    public List<T> queryAll(D condition) {
        T t = supplier.get();
        BeanUtils.copyProperties(condition, t);
        Example example = Example.of(t.getClass());
        return innerRepository.findAll(example);
    }

    @Override
    public PageResult<T> queryPage(D condition, PageParam pageParam) {
        T t = supplier.get();
        BeanUtils.copyProperties(condition, t);
        Example example = Example.of(t.getClass());
        return JpaUtil.toPageResult(innerRepository.findAll(example, JpaUtil.toPageable(pageParam)));
    }
}
