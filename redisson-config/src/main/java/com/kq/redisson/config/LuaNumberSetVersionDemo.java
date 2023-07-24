package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.util.Arrays;

/**
 * LuaNumberCompareDemo
 *
 * @author kq
 * @date 2023-07-20 22:50
 * @since 1.0.0
 */
public class LuaNumberSetVersionDemo {

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

        // 描述:
        // 多线程设置redis的某个key的版本号
        // 如果当前版本号 > redis中的版本号,则设置
        // 如果当前版本号 <= redis中的版本号,则忽略
        // 最后,返回redis中的版本号
        String script = "local curVersion = tonumber(redis.call('get',KEYS[1])); "+
                "local val = ARGV[1]; "+
                "if curVersion == nil then "+
                "   redis.call('set',KEYS[1],val) ; " +
                "elseif curVersion < val then " +
                "   redis.call('set',KEYS[1],val) ; " +
                "end; " +
                "   curVersion = tonumber(redis.call('get',KEYS[1])); " +
                "return incrVersion;"
                ;

        Long value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER, Arrays.asList("curVersion"), "100");
        // guest
        System.out.println("直接执行脚本："+value);



    }

}
