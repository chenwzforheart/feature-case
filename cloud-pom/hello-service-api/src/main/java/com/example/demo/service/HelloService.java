package com.example.demo.service;

import com.example.demo.dto.User;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wenzheng.chen on 2018/1/29.
 */

@RequestMapping("/refactor")
public interface HelloService {

    @RequestMapping(value = "/hello4",method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/hello5",method = RequestMethod.GET)
    public User hello(@RequestHeader(value = "name") String name, @RequestHeader(value = "age") Integer age);

    @RequestMapping(value = "/hello6",method = RequestMethod.POST)
    public String hello(@RequestBody User user);
}
