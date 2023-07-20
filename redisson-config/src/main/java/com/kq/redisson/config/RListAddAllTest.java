package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.List;

public class RListAddAllTest {

    public static void main(String[] args) {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL).setDatabase(12)
                .setPassword("jkct-redis-123456");

        RedissonClient redisson = Redisson.create(config);

        RList listmore = redisson.getList("listmore");

        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        listmore.addAll(list);

    }

}
