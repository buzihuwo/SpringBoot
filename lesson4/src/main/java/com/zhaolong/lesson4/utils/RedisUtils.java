package com.zhaolong.lesson4.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    private StringRedisTemplate template;
    private static RedisUtils redisUtils;

    @PostConstruct
    private void init() {
        redisUtils = this;
        redisUtils.template = this.template;
    }

    public static void setRedisString(String cacheName, String cacheId, String value) {
        ValueOperations<String, String> ops = redisUtils.template.opsForValue();
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        ops.set(cacheKey, value);
    }

    public static String getRedisString(String cacheName, String cacheId) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        ValueOperations<String, String> ops = redisUtils.template.opsForValue();
        try {
            return ops.get(cacheKey);
        } catch (Exception e) {
            return null;
        }
    }

    public static void setRedisHash(String cacheName, String cacheId, String fieid, Object value) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        HashOperations<String, String, String> ops = redisUtils.template.opsForHash();
        ops.put(cacheKey, fieid, JSON.toJSONString(value));
    }

    public static class GetRedisHash{
        public static Map<String, String> getRedisHash(String cacheName, String cacheId) {
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            HashOperations<String, String, String> ops = redisUtils.template.opsForHash();
            try {
                return ops.entries(cacheKey);
            } catch (Exception e) {
                return null;
            }
        }

        public static Object getRedisHashByFieid(String cacheName, String cacheId, String fieid) {
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            HashOperations<String, String, String> ops = redisUtils.template.opsForHash();
            return ops.get(cacheKey, fieid);
        }
    }

    public static class SetRedisList {
        public static void setRedisLeftList(String cacheName, String cacheId, Object value) {
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            ListOperations<String, String> ops = redisUtils.template.opsForList();
            ops.leftPush(cacheKey, JSON.toJSONString(value));
        }

        public static void setRedisRightList(String cacheName, String cacheId, Object value) {
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            ListOperations<String, String> ops = redisUtils.template.opsForList();
            ops.rightPush(cacheKey, JSON.toJSONString(value));
        }
    }

    public static class GetRedisList{
        public static Object getRedisLeftList(String cacheName, String cacheId){
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            ListOperations<String, String> ops = redisUtils.template.opsForList();
            return ops.leftPop(cacheKey);
        }

        public static Object getRedisRightList(String cacheName, String cacheId){
            String cacheKey = String.format("%s:%s", cacheName, cacheId);
            ListOperations<String, String> ops = redisUtils.template.opsForList();
            return ops.rightPop(cacheKey);
        }
    }

    public static void setExpireTime(String cacheName, String cacheId, long time, TimeUnit timeUnit) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        redisUtils.template.expire(cacheKey, time, timeUnit);
    }

    public static boolean isExistence(String cacheName, String cacheId) {
        String cacheKey = String.format("%s:%s", cacheName, cacheId);
        return redisUtils.template.hasKey(cacheKey);
    }

//    @Bean
//    public RedisList createBean() {
//        return new RedisList();
//    }
}
