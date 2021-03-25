package com.kq.collection;

import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.io.IOException;

public class BaseTest {

    public RedissonClient redisson() throws IOException {

        Config config = null;

        config = new Config();
        String prefix = "redis://";

//        String host = "192.168.99.100";
        String host = "172.16.67.21";
        int port = 6379;
        int database = 0;
        String password = "jkct-redis-123456";


        config.useSingleServer()
                .setAddress(prefix + host + ":" + port)
                .setConnectTimeout(10000)
                .setDatabase(database)
                .setPassword(password);

        // redission默认是org.redisson.codec.FstCodec  这里设置为 默认string
        config.setCodec(StringCodec.INSTANCE);

        return Redisson.create(config);


    }


    public void flushdb() throws Exception{

        RedissonClient redissonClient = this.redisson();
        RKeys keys = redissonClient.getKeys();
        keys.flushdb();

    }

}
