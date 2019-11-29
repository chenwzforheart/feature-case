package com.example.login;

import lombok.Data;

import java.util.Date;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Data
public class OnlineUser {

    private String username;

    private String UA;

    private String ip;

    private Date lastLoginTime;

}
