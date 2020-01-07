package com.example.jwt;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author csh9016
 * @date 2020/1/7
 */
public interface BaseService<T extends BaseRecord> {

    T get(Long id,Long userId);

    List<T> query(T condition);

    void add(T record);

    void update(T record);

    void delete(Long id);
}
