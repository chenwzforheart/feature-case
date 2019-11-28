package com.example.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Slf4j
@RestController
public class HomeController {

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(String username, String password, HttpSession httpSession) {
        log.info("username:{},password:{},sessionId:{}",username,password,httpSession.getId());
    }
}
