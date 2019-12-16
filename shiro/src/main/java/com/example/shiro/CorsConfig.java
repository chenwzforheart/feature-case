package com.example.shiro;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * @author csh9016
 * @date 2019/11/29
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:63342");
    }*/

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("http://localhost:63342");
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource path = new UrlBasedCorsConfigurationSource();
        path.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new CorsFilter(path));
        filter.setOrder(-1);
        return filter;
    }
}
