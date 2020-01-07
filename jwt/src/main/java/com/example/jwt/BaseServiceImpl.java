package com.example.jwt;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author csh9016
 * @date 2020/1/7
 */
@Service
public class BaseServiceImpl implements BaseService<BaseRecord> {

    @Override
    public BaseRecord get(Long id,Long userId) {
        //1.查数据库
        BaseRecord one = new BaseRecord();
        Preconditions.checkNotNull(one, String.format("数据%d不存在", one.getId()));

        //2.检查当前操作用户
        if (Objects.nonNull(userId)) {
            Preconditions.checkState(userId.equals(one.getUserId()), "访问数据越权");
        }
        //3.返回
        return one;
    }

    @Override
    public List<BaseRecord> query(BaseRecord condition) {
        Preconditions.checkNotNull(condition.getUserId(), "用户未登录");
        BaseRecord query = new BaseRecord();
        query.setUserId(condition.getUserId());
        return null;
    }

    @Override
    public void add(BaseRecord record) {
        //1.直接新增
        Preconditions.checkNotNull(record.getUserId(), "用户未登录");
        BaseRecord one = new BaseRecord();
        one.setUserId(record.getUserId());
    }

    @Override
    public void update(BaseRecord record) {
        //1.先查询
        BaseRecord one = get(record.getId(), record.getUserId());
        //2.后更新
    }

    @Override
    public void delete(Long id) {
        //1.先查询
        BaseRecord one = get(id, 1L);
        //2.后删除
    }
}
