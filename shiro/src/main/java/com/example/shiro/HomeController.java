package com.example.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordMatcher passwordMatcher;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestBody LoginInfo loginInfo, HttpServletRequest request, HttpSession httpSession) {
        Object user = SecurityUtils.getSubject().getPrincipal();
        log.info("用户名：{}",user);
        return user;
        /*String remoteAddr = request.getRemoteAddr();
        httpSession.setAttribute("loginFlag", true);
        httpSession.setAttribute("loginIP", remoteAddr);
        httpSession.setAttribute("loginUser", loginInfo.getUsername());
        log.info("username:{},password:{},sessionId:{},ip:{}", loginInfo.getUsername(), loginInfo.getPassword(), httpSession.getId(), remoteAddr);*/
    }

    //@RequiresRoles("ROLE_ADMIN")
    @RequiresPermissions("/admin.jsp")
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
        Subject login = SecurityUtils.getSubject();
        login.logout();
        return "LOGOUT";
    }

    @RequestMapping(value = "/code", method = {RequestMethod.POST, RequestMethod.GET})
    public String code(HttpSession session) {
        session.setAttribute("code", "1234");
        return "CODE:1234";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody LoginInfo login, HttpSession session) {
        //1.验证码
        Object code = session.getAttribute("code");
        if (code != null && Objects.equals(code, login.getCode())) {
            //2.密码加密
            //3.注册
            addAccount(login);
            //4.注销session
            session.invalidate();
            return "REGISTER SUCCESS";
        }

        return "REGISTER FAIL";
    }

    private void addAccount(LoginInfo login) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into t_user(username,password) values (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, login.getUsername());
                ps.setString(2, passwordMatcher.getPasswordService().encryptPassword(login.getPassword()));
                return ps;
            }
        }, keyHolder);
        jdbcTemplate.update("insert into t_user_role(user_id,role_id) values (?,?)", keyHolder.getKey().intValue(), 1);
    }
}
