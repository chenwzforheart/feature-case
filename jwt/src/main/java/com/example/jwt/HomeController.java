package com.example.jwt;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Slf4j
@RestController
public class HomeController {

    @Autowired
    private List<Filter> filters;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginInfo loginInfo, @CookieValue("code_vr") String codeVr, HttpServletResponse response) {
        log.info("code key:{}", codeVr);
        HashMap<String, String> map = Maps.newHashMap();
        map.put("name", loginInfo.getUsername());
        Object code = redisTemplate.boundValueOps("code:login:" + codeVr).get();
        response.addCookie(delCookie("code_vr"));
        if (Objects.equals(loginInfo.getCode(), code)) {
            log.info("验证码：{}", code);
            log.info("用户名：{}", loginInfo);
            Cookie cookie = new Cookie(JwtHelper.HEADER, JwtHelper.genToken(map));
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(3600);
            response.addCookie(cookie);
            return "登录成功";
        }else {
            return "登录失败";
        }

    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String login1(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        return String.format("11:username:%s,password:%s,sessionId:%s,ip:%s", loginInfo.getUsername(), loginInfo.getPassword(), "none", remoteAddr);
    }

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String login2(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        return String.format("22:username:%s,password:%s,sessionId:%s,ip:%s", loginInfo.getUsername(), loginInfo.getPassword(), "none", remoteAddr);
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized() {
        return "UNAUTHORIZED";
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "SUCCESS";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        return "LOGOUT";
    }

    @RequestMapping(value = "/code", method = {RequestMethod.POST, RequestMethod.GET})
    public String code(HttpServletResponse response) {
        String key = UUID.randomUUID().toString();
        redisTemplate.boundValueOps("code:login:" + key).set("1234",30,TimeUnit.SECONDS);
        response.addCookie(addCookie("code_vr",key));
        return "CODE:有效期30秒";
    }

    private Cookie addCookie(String key, String val) {
        Cookie cookie = new Cookie(key, val);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    private Cookie delCookie(String key) {
        Cookie cookie = new Cookie(key,"");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody LoginInfo login) {
        //1.验证码
        Object code = "";
        if (code != null && Objects.equals(code, login.getCode())) {
            //2.密码加密
            //3.注册
            //4.注销session,
            return "REGISTER SUCCESS";
        }

        return "REGISTER FAIL";
    }

}
