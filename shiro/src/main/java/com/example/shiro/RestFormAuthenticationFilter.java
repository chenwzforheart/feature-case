package com.example.shiro;

import com.alibaba.fastjson.JSON;
import com.example.shiro.comm.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author csh9016
 * @date 2019/12/16
 */
@Slf4j
public class RestFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

            //未登录
            if (isLoginSubmission(request, response)) {
                //如果请求loginURL
                if (isLoginRequest(request, response)) {
                    if (log.isTraceEnabled()) {
                        log.trace("Login submission detected.  Attempting to execute login.");
                    }
                    return executeLogin(request, response);
                }
                //提示登录
                else {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.getWriter().println(JSON.toJSONString(CommonResult.unauthorized("用户未登录")));
                    response.getWriter().flush();
                    return false;
                }

            }
            //已登录
            else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                //allow them to see the login page ;)
                return true;
            }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        UsernamePasswordToken user = new UsernamePasswordToken();
        try {
            String s = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
            user = JSON.parseObject(s, UsernamePasswordToken.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createToken(user.getUsername(), new String(user.getPassword()), request, response);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(CommonResult.success("登录成功")));
        response.getWriter().flush();

        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,ServletRequest request, ServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            response.getWriter().println(JSON.toJSONString(CommonResult.failed("登录失败")));
            response.getWriter().flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return false;
    }
}
