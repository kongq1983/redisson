package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.Collections;
import java.util.List;

/**
 * LuaStringDemo
 *
 * @author kq
 * @date 2023-07-20 22:21
 * @since 1.0.0
 */
public class LuaStringDemo {
    public static void main(String[] args) {

        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL)
//                .setPassword("jkct-redis-123456")
        ;

        RedissonClient redisson = Redisson.create(config);
        redisson.getBucket("name").set("admin");

        RScript rScript = redisson.getScript();

        String script = "redis.call('set',KEYS[1], ARGV[1]) "+
                "return redis.call('get', KEYS[1])";


        String value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.VALUE, Collections.singletonList("name"), "guest");
        // guest
        System.out.println("直接执行脚本："+value);



    }

}
