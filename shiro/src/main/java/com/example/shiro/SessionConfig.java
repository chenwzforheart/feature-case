package com.example.shiro;

import com.example.shiro.cache.CacheJdbcRealm;
import com.example.shiro.cache.PassService;
import com.example.shiro.cache.RedisCacheM;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author csh9016
 * @date 2019/12/12
 */
@Configuration
public class SessionConfig {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public PasswordMatcher passwordMatcher() {
        PasswordMatcher passwordMatcher = new PasswordMatcher();
        //passwordMatcher.setPasswordService(passService());
        return passwordMatcher;
    }

    @Bean
    public PassService passService() {
        return new PassService();
    }

    @Bean
    public SecurityManager securityManager(ObjectProvider<DataSource> dataSources) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        CacheJdbcRealm jdbcRealm = new CacheJdbcRealm();
        jdbcRealm.setAuthenticationQuery("select password from t_user where username = ?");
        jdbcRealm.setUserRolesQuery("select c.name as role_name from t_user a left join t_user_role b on b.user_id = a.id left join t_role c on c.id = b.role_id" +
                " where username = ?");
        jdbcRealm.setPermissionsQuery("select c.res_string as permission from t_role a left join t_resc_role b on b.role_id = a.id LEFT JOIN t_resc c on c.id = b.resc_id" +
                " where a.name = ?");
        jdbcRealm.setDataSource(dataSources.getIfAvailable());
        jdbcRealm.setPermissionsLookupEnabled(true);
        jdbcRealm.setCacheManager(new RedisCacheM(redisTemplate));

        jdbcRealm.setCredentialsMatcher(passwordMatcher());
        securityManager.setRealm(jdbcRealm);

        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        filter.setLoginUrl("/login");
        filter.setUnauthorizedUrl("/unauthorized");
        filter.setSuccessUrl("/success");
        HashMap<String, Filter> customFilter = new HashMap<>();
        RestFormAuthenticationFilter ff = new RestFormAuthenticationFilter();
        ff.setRememberMeParam("rememberMe");

        //user过滤器支持remember，authc必须通过登录
        RestUserFilter rr = new RestUserFilter();
        customFilter.put("restAuthc", ff);
        customFilter.put("restUser", rr);
        filter.setFilters(customFilter);

        //设置过滤器链
        HashMap<String, String> filterDef = new HashMap<>();
        filterDef.put("/login", "restAuthc");
        filterDef.put("/code", "anon");
        filterDef.put("/register", "anon");
        filterDef.put("/login1", "restAuthc");
        filterDef.put("/login2", "restUser");
        filterDef.put("/**", "restAuthc");
        filter.setFilterChainDefinitionMap(filterDef);
        return filter;
    }

    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie cookie = new SimpleCookie();
        cookie.setName("rememberMe");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 3600);
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }
}
