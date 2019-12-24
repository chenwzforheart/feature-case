package com.example.shiro;

import com.alibaba.fastjson.JSON;
import com.example.shiro.comm.CommonResult;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author csh9016
 * @date 2019/12/24
 */
public class RestUserFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(CommonResult.forbidden("")));
        response.getWriter().flush();
        return false;
    }
}
