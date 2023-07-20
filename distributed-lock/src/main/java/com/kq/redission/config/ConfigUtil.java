package com.kq.redission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author kq
 * @date 2021-03-26 14:09
 * @since 2020-0630
 */
public class ConfigUtil {

    protected static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);

    public static RedissonClient getRedissonClient() throws IOException {

        org.redisson.config.Config config = null;

        config = new org.redisson.config.Config();
        String prefix = "redis://";


        config.useSingleServer()
                .setAddress(prefix + "172.16.5.1" + ":" + 6379);

        // redission默认是org.redisson.codec.FstCodec  这个加字段、减字段会出现不兼容  这里设置为 默认string
//        config.setCodec(StringCodec.INSTANCE);
        config.setCodec(JsonJacksonCodec.INSTANCE);


        return Redisson.create(config);


    }

}
