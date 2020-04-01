package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.List;

public class SingleConfigTest {

    public static void main(String[] args) {
        // connects to 127.0.0.1:6379 by default
//        RedissonClient redisson = Redisson.create();

        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL)
        .setPassword("jkct-redis-123456");

        RedissonClient redisson = Redisson.create(config);

        RList list = redisson.getList("list:list5");
        list.add("one");
        list.add("two");

        List<String> loadAll = list.readAll();

        loadAll.forEach(d->{
            System.out.println("data="+d);
        });


    }

}
