package com.example.jwt;

import lombok.Data;

/**
 * @author csh9016
 * @date 2019/12/30
 */
@Data
public class User {
    private String username;
    private String password;
    private String email;
    private String token;
}
