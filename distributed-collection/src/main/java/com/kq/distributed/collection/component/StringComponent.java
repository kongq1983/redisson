package com.kq.distributed.collection.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class StringComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void save(String key,String value) {
        stringRedisTemplate.opsForValue().set(key,value);
    }


    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

}
