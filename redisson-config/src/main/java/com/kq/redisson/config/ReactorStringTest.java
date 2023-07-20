package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import org.redisson.Redisson;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

public class ReactorStringTest {

    public static void main(String[] args) throws Exception{

        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ Util.REDIS_SERVER_URL).setDatabase(12)
                .setPassword("jkct-redis-123456");

        RedissonReactiveClient redisson = Redisson.createReactive(config);

        Mono<Void> mono = redisson.getBucket("mystring").set("hello1")
                .doOnError(e-> {
            e.printStackTrace();
            System.out.println("error="+e);
        });

        mono.subscribe(s->{
            System.out.println("result s="+s);
        });

        redisson.getBucket("mystring1",StringCodec.INSTANCE).get().subscribe(s->{
            System.out.println("mystring1 string_test_key load data : "+s);
        });

        redisson.getBucket("mystring").get().subscribe(s->{
            System.out.println("mystring0 string_test_key load data : "+s); // key 不存在 不会调用这个
        });
/*
        Mono<Object> mono = redisson.getBucket("string_test_key", StringCodec.INSTANCE).get();


        mono.doOnNext(s->{
            System.out.println("1 string_test_key load data : "+s);
        });

        System.out.println("-------------------------------1------------------------------");

        Thread.sleep(3000L);

        mono.subscribe(s->{
            System.out.println("2 string_test_key load data : "+s);
        });
*/

        TimeUnit.MINUTES.sleep(10);

    }

}
