package com.example.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author csh9016
 * @date 2019/12/18
 */
public class RedisCacheM implements CacheManager {

    private RedisTemplate redisTemplate;

    public RedisCacheM(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = new CacheM(redisTemplate);
        return cache;
    }
}
