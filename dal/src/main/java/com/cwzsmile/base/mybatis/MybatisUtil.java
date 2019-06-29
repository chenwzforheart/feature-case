package com.cwzsmile.base.mybatis;

import com.cwzsmile.base.PageParam;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.apache.ibatis.session.RowBounds;

/**
 * Created by csh9016 on 2019/6/28.
 */
public class MybatisUtil {

    public static PageBounds toPageBounds(PageParam pageParam) {
        return new PageBounds(pageParam.getPage(), pageParam.getSize());
    }

    public static RowBounds toRowBounds(PageParam pageParam) {
        return new RowBounds((pageParam.getPage() - 1) * pageParam.getSize(), pageParam.getSize());
    }

}
