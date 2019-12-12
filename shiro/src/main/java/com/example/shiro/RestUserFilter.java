package com.example.shiro;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author csh9016
 * @date 2019/12/12
 */
@Component
public class RestUserFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/unauthorized");
        dispatcher.forward(request, response);
        return false;
    }
}
