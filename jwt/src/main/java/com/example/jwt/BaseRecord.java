package com.example.jwt;

import lombok.Data;

/**
 * @author csh9016
 * @date 2020/1/7
 */
@Data
public class BaseRecord {

    private Long id;

    private Long userId;

    private Integer logicDelete;

    /**
     * 两层数据，用户层和管理员
     */
    private Boolean superAdmin;
}
