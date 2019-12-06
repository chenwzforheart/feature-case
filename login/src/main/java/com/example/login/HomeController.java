package com.example.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Slf4j
//@CrossOrigin(value = "http://localhost:63342",maxAge = 3600)
@RestController
public class HomeController {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        String remoteAddr = request.getRemoteAddr();
        httpSession.setAttribute("loginFlag",true);
        httpSession.setAttribute("loginIP",remoteAddr);
        httpSession.setAttribute("loginUser",loginInfo.getUsername());
        log.info("username:{},password:{},sessionId:{},ip:{}",loginInfo.getUsername(),loginInfo.getPassword(),httpSession.getId(),remoteAddr);
    }

    @RequestMapping(value = "/siteInfo",method = RequestMethod.GET)
    public String siteInfo() {
        Set keys = redisTemplate.keys("spring:session:sessions:expires:*");
        return String.format("在线人数:%d,登录用户:%d",keys.size(),0);
    }
}
