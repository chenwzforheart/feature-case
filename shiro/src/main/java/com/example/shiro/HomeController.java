package com.example.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author csh9016
 * @date 2019/11/28
 */
@Slf4j
//@CrossOrigin(value = "http://localhost:63342",allowCredentials = "true",maxAge = 3600)
@RestController
public class HomeController {

    @Autowired
    private List<Filter> filters;


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        String remoteAddr = request.getRemoteAddr();
        httpSession.setAttribute("loginFlag",true);
        httpSession.setAttribute("loginIP",remoteAddr);
        httpSession.setAttribute("loginUser",loginInfo.getUsername());
        log.info("username:{},password:{},sessionId:{},ip:{}",loginInfo.getUsername(),loginInfo.getPassword(),httpSession.getId(),remoteAddr);
    }

    //@RequiresRoles("ROLE_ADMIN")
    @RequiresPermissions("/admin.jsp")
    @RequestMapping(value = "/login1",method = RequestMethod.POST)
    public String login1(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        String remoteAddr = request.getRemoteAddr();
        return String.format("username:%s,password:%s,sessionId:%s,ip:%s", loginInfo.getUsername(), loginInfo.getPassword(), httpSession.getId(), remoteAddr);
    }

    @RequestMapping(value = "/unauthorized")
    public String unauthorized() {
        return "UNAUTHORIZED";
    }

    @RequestMapping(value = "/success")
    public String success() {
        return "SUCCESS";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public String logout() {
        Subject login = SecurityUtils.getSubject();
        login.logout();
        return "LOGOUT";
    }

}
