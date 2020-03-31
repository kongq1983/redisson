package com.kq.distributed.collection.component;

import com.kq.redission.util.DateUtil;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class MapComponent {

    public static final String KEY = "user:register";
    public static final String MAP_KEY = "userMap";

    @Autowired
    private RedissonClient redissonClient;

    public String putIfAbsent(String username){

        RMap<String, String> map = redissonClient.getMap(MAP_KEY);

        String time = map.putIfAbsent(username, DateUtil.getFormatDate());

        return time;

    }


    public String put(String username){

        RMap<String, String> map = redissonClient.getMap(MAP_KEY);

        String time = map.put(username, DateUtil.getFormatDate());

        return time;

    }

    public Collection<String> values(){

        RMap<String, String> map = redissonClient.getMap(MAP_KEY);

        return map.values();

    }



}
