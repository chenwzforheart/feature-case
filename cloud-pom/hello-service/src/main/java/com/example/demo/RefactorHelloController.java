package com.example.demo;

import com.example.demo.dto.User;
import com.example.demo.service.HelloService;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wenzheng.chen on 2018/1/29.
 */

@RestController
public class RefactorHelloController implements HelloService{

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public User hello(String name, Integer age) {
        return new User(name, age);
    }

    @Override
    public String hello(User user) {
        return "Hello " + user.getName() + ", " + user.getAge();
    }
}
