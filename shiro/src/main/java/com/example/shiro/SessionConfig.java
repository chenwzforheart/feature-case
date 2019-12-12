package com.example.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;

/**
 * @author csh9016
 * @date 2019/12/12
 */
@Configuration
public class SessionConfig {

    @Autowired
    private RestUserFilter restUserFilter;

    @Bean
    public SecurityManager securityManager() {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {

        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager());
        filter.setLoginUrl("/login");
        filter.setUnauthorizedUrl("/unauthorized");
        filter.setSuccessUrl("/success");
        HashMap<String, Filter> customFilter = new HashMap<>();
        customFilter.put("restUser", restUserFilter);
        filter.setFilters(customFilter);

        //设置过滤器链
        HashMap<String, String> filterDef = new HashMap<>();
        filterDef.put("/**", "restUser");
        filter.setFilterChainDefinitionMap(filterDef);
        return filter;
    }
}
