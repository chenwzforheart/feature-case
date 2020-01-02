package com.example.jwt;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Slf4j
@RestController
public class HomeController {

    @Autowired
    private List<Filter> filters;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginInfo loginInfo, @CookieValue("code_vr") String codeVr, HttpServletResponse response) {
        log.info("code key:{}",codeVr);
        HashMap<String, String> map = Maps.newHashMap();
        map.put("name", loginInfo.getUsername());
        Cookie cookie = new Cookie(JwtHelper.HEADER, JwtHelper.genToken(map));
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        log.info("用户名：{}", loginInfo);
        return loginInfo;
    }

    @RequestMapping(value = "/login1", method = RequestMethod.POST)
    public String login1(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        String remoteAddr = request.getRemoteAddr();
        return String.format("11:username:%s,password:%s,sessionId:%s,ip:%s", loginInfo.getUsername(), loginInfo.getPassword(), httpSession.getId(), remoteAddr);
    }

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String login2(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        String remoteAddr = request.getRemoteAddr();
        return String.format("22:username:%s,password:%s,sessionId:%s,ip:%s", loginInfo.getUsername(), loginInfo.getPassword(), httpSession.getId(), remoteAddr);
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
        Cookie cookie = new Cookie("code_vr", UUID.randomUUID().toString());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        //session.setAttribute("code", "1234");
        return "CODE:1234";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody LoginInfo login, HttpSession session) {
        //1.验证码
        Object code = session.getAttribute("code");
        if (code != null && Objects.equals(code, login.getCode())) {
            //2.密码加密
            //3.注册
            //4.注销session
            session.invalidate();
            return "REGISTER SUCCESS";
        }

        return "REGISTER FAIL";
    }

}
