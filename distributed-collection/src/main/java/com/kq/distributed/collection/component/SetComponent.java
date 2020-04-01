package com.kq.distributed.collection.component;

import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetComponent {

    protected static Logger logger = LoggerFactory.getLogger(SetComponent.class);

    public static final String KEY = "set:key";

    @Autowired
    private RedissonClient redissonClient;

    public void add(String data) {

        RSet<String> set = redissonClient.getSet(KEY);
        set.add(data);
//        list.add(data);

    }

    public boolean contains(String data) {

        RSet<String> set = redissonClient.getSet(KEY);
        return set.contains(data);
//        list.add(data);
    }


    public void remove(String data) {

        RSet<String> set = redissonClient.getSet(KEY);
        set.remove(data);

    }


}
