package com.cwzsmile.base.page;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import lombok.Data;
import org.apache.ibatis.session.RowBounds;

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

    public PageBounds toPageBounds() {
        return new PageBounds(page, size);
    }

    public RowBounds toRowBounds() {
        return new RowBounds((page - 1) * size, size);
    }

    public static PageParam newInstance(Integer size, Integer page) {
        return new PageParam(size, page);
    }
}
