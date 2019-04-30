package com.cwzsmile.mapper;

import com.cwzsmile.base.BaseMapper;
import com.cwzsmile.model.CmsHelp;

public interface CmsHelpMapper extends BaseMapper<CmsHelp> {
    int deleteByPrimaryKey(Long id);

    CmsHelp selectByPrimaryKey(Long id);
}