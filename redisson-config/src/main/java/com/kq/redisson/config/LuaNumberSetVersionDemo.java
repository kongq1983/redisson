package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * LuaNumberCompareDemo
 *
 * @author kq
 * @date 2023-07-20 22:50
 * @since 1.0.0
 */
public class LuaNumberSetVersionDemo {

    public static void main(String[] args) throws Exception{

        Config config = new Config();
        config.setCodec(StringCodec.INSTANCE);
        SingleServerConfig singleServerConfig = config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL);

        if(args.length>0) {
            singleServerConfig.setPassword(args[0]).setDatabase(15);
        }


        RedissonClient redisson = Redisson.create(config);
//        redisson.getBucket("name").set("admin");
//        RBucket<Long> rBucket = redisson.getBucket("curVersion");
//        rBucket.set(10L);
//        RBucket<Long> r1Bucket = redisson.getBucket("incrVersion");
//        r1Bucket.set(2L);

        RScript rScript = redisson.getScript();

        // 描述:
        // 多线程设置redis的某个key的版本号
        // 如果当前版本号 > redis中的版本号,则设置
        // 如果当前版本号 <= redis中的版本号,则忽略
        // 最后,返回redis中的版本号
        final String script = "local curVersion = tonumber(redis.call('get',KEYS[1])); "+
                "local val = tonumber(ARGV[1]); "+
                "if curVersion == nil then "+
                "   redis.call('set',KEYS[1],val) ; " +
                "elseif curVersion < val then " +
                "   redis.call('set',KEYS[1],val) ; " +
                "end; " +
                "   curVersion = tonumber(redis.call('get',KEYS[1])); " +
                "return curVersion;"
                ;

        int val = 110;

        Long value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER, Arrays.asList("test:current:version"), val);
        // guest
        System.out.println("直接执行脚本,返回值为："+value+",传入值为:"+val);


        multiThread(rScript,script);


        TimeUnit.SECONDS.sleep(5);
    }

    private static void multiThread(RScript rScript,String script){

        final int values[] = {150,128,118,320,90,300,250};

        Thread[] threads = new Thread[values.length];

        for(int i=0;i<threads.length;i++) {
            int finalI = i;
            threads[i] = new Thread(()->{
                Long value = rScript.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.INTEGER, Arrays.asList("test:current:version"), values[finalI]);
                System.out.println("thread["+finalI+"], return value="+value+" , in parameter:"+values[finalI]);
            });
        }


        for(Thread thread : threads){
            thread.start();
        }

    }

}
