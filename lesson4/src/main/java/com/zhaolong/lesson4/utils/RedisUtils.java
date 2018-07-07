package com.zhaolong.lesson4.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUtils {
    @Autowired
    private StringRedisTemplate template;

    public void setRedisString(String cacheName, String cacheId, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        ops.set(cacheKey, value);
    }

    public String getRedisString(String cacheName, String cacheId) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        ValueOperations<String, String> ops = template.opsForValue();
        try {
            return ops.get(cacheKey);
        } catch (Exception e) {
            return null;
        }

    }

    public void setRedisHash(String cacheName, String cacheId, String fieid, Object value) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        HashOperations<String, String, String> ops = template.opsForHash();
        ops.put(cacheKey, fieid, JSON.toJSONString(value));
    }

    public Map<String, String> getRedisHash(String cacheName, String cacheId) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        HashOperations<String, String, String> ops = template.opsForHash();
        try {
            return ops.entries(cacheKey);
        } catch (Exception e) {
            return null;
        }
    }

    public Object getRedisHashByFieid(String cacheName, String cacheId, String fieid) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        HashOperations<String, String, String> ops = template.opsForHash();
        return ops.get(cacheKey, fieid);
    }

    public void setExpireTime(String cacheName, String cacheId, long time, TimeUnit timeUnit) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        template.expire(cacheKey, time, timeUnit);
    }

    public boolean isExistence(String cacheName, String cacheId) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        return template.hasKey(cacheKey);
    }
}
