package com.example.login;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;

/**
 * @author csh9016
 * @date 2019/12/4
 */
@Configuration
public class CsrfConfig {

    @Bean
    public FilterRegistrationBean csrfFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        registration.setFilter(new CsrfFilter(cookieCsrfTokenRepository));
        registration.addUrlPatterns("/*");
        return registration;
    }
}
