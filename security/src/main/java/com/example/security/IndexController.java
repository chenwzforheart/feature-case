package com.example.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author csh9016
 * @date 2019/12/9
 */
@RestController
public class IndexController {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String hello1() {
        return "hello1";
    }

    @RequestMapping(value = "/world",method = RequestMethod.GET)
    public String world() {
        return "world";
    }
}
