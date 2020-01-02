package com.example.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author csh9016
 * @date 2019/12/30
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AuthHandlerInterceptor authHandlerInterceptor() {
        return new AuthHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authHandlerInterceptor()).excludePathPatterns("/login","/code").addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:63342");
    }
}
