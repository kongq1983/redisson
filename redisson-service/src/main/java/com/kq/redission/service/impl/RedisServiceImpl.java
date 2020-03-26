package com.kq.redission.service.impl;

import com.kq.redission.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate<String,String> redisTemplate;




    @Override
    public void setStringValue(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void setStringValueTimeout(String key, String value, int timeout) {
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }

    @Override
    public String getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
