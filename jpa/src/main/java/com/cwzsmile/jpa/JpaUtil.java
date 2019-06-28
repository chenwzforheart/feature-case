package com.cwzsmile.jpa;

import com.cwzsmile.base.PageParam;
import com.cwzsmile.base.PageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created by csh9016 on 2019/6/28.
 */
public class JpaUtil {

    public static Pageable toPageable(PageParam pageParam) {
        return PageRequest.of(pageParam.getPage(), pageParam.getSize());
    }

    public static PageResult toPageResult(Page page) {
        PageImpl page1 = (PageImpl) page;
        return PageResult.newInstance(page1.getContent());
    }
}
