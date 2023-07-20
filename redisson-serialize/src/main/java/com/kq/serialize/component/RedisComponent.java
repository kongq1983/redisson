package com.kq.serialize.component;

import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * @date 2022-04-13 11:00
 * @since 2020-0630
 */
@Component
public class RedisComponent {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    public void setValue(String key,String val) {
        redissonClient.getBucket(key).set(val,1, TimeUnit.HOURS);
    }

    public String getValue(String key) {
        Object obj = this.redissonClient.getBucket(key).get();
        return obj!=null?obj.toString():null;
    }


}
