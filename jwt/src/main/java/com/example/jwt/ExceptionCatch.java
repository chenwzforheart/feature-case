package com.example.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author csh9016
 * @date 2020/1/2
 */
@ControllerAdvice
public class ExceptionCatch {

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseBody
    public Map handleException(Exception e) {
        return ImmutableMap.of("code", "201", "msg", e.getMessage());
    }
}
