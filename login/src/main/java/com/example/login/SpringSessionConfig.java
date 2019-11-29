package com.example.login;

import org.springframework.context.annotation.Bean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author csh9016
 * @date 2019/11/29
 */
//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200)
public class SpringSessionConfig {

    @Bean
    public CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 取消samesite
        cookieSerializer.setSameSite(null);
        return cookieSerializer;
    }
}
