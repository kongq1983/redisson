package com.kq.spring.redis;

import com.kq.spring.redis.component.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: kq
 * @date: 2023-08-11 08:52:03
 * @since: 1.0.0
 * @description:
 */

@SpringBootApplication(scanBasePackages="com.kq.spring.redis")
@ImportResource(locations = { "classpath:ctx_redis_cluster.xml" })
public class SpringRedisApplication {

    Logger logger = LoggerFactory.getLogger(SpringRedisApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringRedisApplication.class, args);

        RedisService redisService = context.getBean(RedisService.class);

        String[] keys = {"one","aerson","dog","glad","eleven"};

        for(String key : keys) {
            redisService.set(key,key);
        }


    }

}
