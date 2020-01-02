package com.example.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author csh9016
 * @date 2019/12/30
 */
public class JwtHelper {

    private static final String SECRET = "session_secret";

    private static final String ISSUER = "me_user";

    public static final String HEADER = "Authorization";

    public static String genToken(Map<String, String> claims) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
            JWTCreator.Builder builder = JWT.create().withIssuer(ISSUER).withExpiresAt(DateUtils.addDays(new Date(), 1));
            claims.forEach((k, v) -> builder.withClaim(k, v));
            return builder.sign(algorithm).toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> map = Maps.newHashMap();
        map.put("name", "nihao");
        String result = genToken(map);
        System.out.println(verifyToken(result));

    }

    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        Map<String, Claim> map = jwt.getClaims();
        Map<String, String> resultMap = Maps.newHashMap();
        map.forEach((k, v) -> resultMap.put(k, v.toString()));
        return resultMap;
    }

    /**
     * 登录
     *
     * @param user
     */
    private void onLogin(User user) {
        String token = JwtHelper.genToken(ImmutableMap.of("name", user.getUsername(), "email", user.getEmail(), "ts", Instant.now().getEpochSecond() + ""));
        user.setToken(token);
    }

    private void invalidate(String token) {
        Map<String, String> map = JwtHelper.verifyToken(token);
        redisTemplate.delete(map.get("email"));
    }

    private RedisTemplate redisTemplate;

    private String renewToken(String token, String email) {
        redisTemplate.opsForValue().set(email, token);
        redisTemplate.expire(email, 30, TimeUnit.MINUTES);
        return token;
    }

    public User getLoginedUserByToken(String token) {
        Map<String, String> map = null;
        map = JwtHelper.verifyToken(token);
        String name = map.get("name");
        Long expire = redisTemplate.getExpire(name);
        if (expire > 0) {
            renewToken(token, name);
            User user = getUserByName(name);
            return user;
        }
        throw new RuntimeException("用户找不到");
    }

    public User getUserByName(String name) {
        return null;
    }
}
