package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.Collections;

/**
 * LuaNumberCompareDemo
 *
 * @author kq
 * @date 2023-07-20 22:50
 * @since 1.0.0
 */
public class LuaNumberCompareDemo {

    public static void main(String[] args) {

        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL)
//                .setPassword("jkct-redis-123456")
        ;

        RedissonClient redisson = Redisson.create(config);
//        redisson.getBucket("name").set("admin");
        RBucket<Long> rBucket = redisson.getBucket("curVersion");
        rBucket.set(10L);
        RBucket<Long> r1Bucket = redisson.getBucket("incrVersion");
        r1Bucket.set(2L);

        RScript rScript = redisson.getScript();

        String script = "local curVersion = tonumber(redis.call('get',KEYS[1])); "+
                "local incrVersion = tonumber(redis.call('get',KEYS[2])); "+
                "if incrVersion < curVersion  then "+
                " redis.call('set',KEYS[2],curVersion) ; " +
                " incrVersion = redis.call('incr',KEYS[2]) ; " +
                "end; " +
                "return incrVersion;"
                ;

        Long value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER, Arrays.asList("curVersion","incrVersion"), "100");
        // guest
        System.out.println("直接执行脚本："+value);



    }

}
