package com.example.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author csh9016
 * @date 2019/12/18
 */
public class CacheM implements Cache<Object,Object> {

    private RedisTemplate<Object, Object> redisTemplate;

    public CacheM(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object get(Object key) throws CacheException {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 120, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        Object val = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(key);
        return val;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Set<Object> keys() {
        return null;
    }

    @Override
    public Collection<Object> values() {
        return null;
    }
}
