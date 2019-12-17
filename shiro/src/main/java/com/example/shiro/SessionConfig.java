package com.example.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author csh9016
 * @date 2019/12/12
 */
@Configuration
public class SessionConfig {


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
    public SecurityManager securityManager(ObjectProvider<DataSource> dataSources) {
        DefaultSecurityManager securityManager = new DefaultWebSecurityManager();
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setAuthenticationQuery("select password from t_user where username = ?");
        jdbcRealm.setUserRolesQuery("select c.name as role_name from t_user a left join t_user_role b on b.user_id = a.id left join t_role c on c.id = b.role_id" +
                " where username = ?");
        jdbcRealm.setPermissionsQuery("select c.res_string as permission from t_role a left join t_resc_role b on b.role_id = a.id LEFT JOIN t_resc c on c.id = b.resc_id" +
                " where a.name = ?");
        jdbcRealm.setDataSource(dataSources.getIfAvailable());
        securityManager.setRealm(jdbcRealm);
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
        customFilter.put("restAuthc", new RestFormAuthenticationFilter());
        filter.setFilters(customFilter);

        //设置过滤器链
        HashMap<String, String> filterDef = new HashMap<>();
        filterDef.put("/**", "restAuthc");
        filter.setFilterChainDefinitionMap(filterDef);
        return filter;
    }
}
