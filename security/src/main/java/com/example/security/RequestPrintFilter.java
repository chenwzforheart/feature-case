package com.example.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.UUID;

/**
 * @author csh9016
 * @date 2019/12/6
 */
@Slf4j
@Configuration
public class RequestPrintFilter {

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(printFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }

    private Filter printFilter() {
        return new Filter() {
            @Override
            public void init(FilterConfig filterConfig) {
                log.info("PrintFilter init Success");
            }

            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                HttpServletResponse response = (HttpServletResponse) servletResponse;

                if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    Enumeration<String> headerNames = request.getHeaderNames();
                    StringBuilder sbd = new StringBuilder();
                    sbd.append(String.format("Request URL:%s Request Method:%s\n", request.getRequestURL(), request.getMethod()));
                    while (headerNames.hasMoreElements()) {
                        String key = headerNames.nextElement();
                        sbd.append(String.format("%s:%s\n", key, request.getHeader(key)));
                    }
                    log.info(sbd.toString());
                }
                chain.doFilter(request, response);
            }

            @Override
            public void destroy() {
                log.info("PrintFilter destroy");
            }
        };
    }
}
