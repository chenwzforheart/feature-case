package com.example.demo;

import com.example.demo.dto.*;
import com.example.demo.dto.User;
import com.example.demo.service.*;

/**
 * Created by wenzheng.chen on 2018/1/29.
 */
public class HelloServiceFallback implements HelloService {


    @Override
    public String hello() {
        return "error";
    }

    @Override
    public String hello(String name) {
        return "error";
    }

    @Override
    public com.example.demo.User hello(String name, Integer age) {
        return new com.example.demo.User("未知", 0);
    }

    @Override
    public String hello(com.example.demo.User user) {
        return "error";
    }
}
