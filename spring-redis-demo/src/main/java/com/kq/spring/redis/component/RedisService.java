package com.kq.spring.redis.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * @date 2022-05-18 15:54
 * @since
 */
@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 默认过期30天
     * @param key
     * @return
     */
//    public Long incr(String key) {
//        return stringRedisTemplate.opsForValue().increment(key);
//    }
//
//
//    public Long incr(String key,long timeout, TimeUnit unit) {
//        stringRedisTemplate.expire(key,timeout, unit);
//        return stringRedisTemplate.opsForValue().increment(key);
//    }

    /**
     * 增长值
     * @param key
     * @param timeout
     * @param unit
     * @param addValue
     * @return
     */
    public Long incr(String key,long timeout, TimeUnit unit,Integer addValue) {
        stringRedisTemplate.expire(key,timeout, unit);
        return stringRedisTemplate.opsForValue().increment(key,addValue);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }


    public void set(String key, String value,TimeUnit timeUnit, long timeout) {
        stringRedisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    public boolean hasKey(String key) {
        return this.stringRedisTemplate.hasKey(key);
    }

    public void delete(String key) {
        this.stringRedisTemplate.delete(key);
    }


}
