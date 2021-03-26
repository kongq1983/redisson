package com.kq.shopping.cart.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author kq
 * @date 2021-03-26 8:14
 * @since 2020-0630
 */
@Configuration
public class Config {

    protected Logger logger = LoggerFactory.getLogger(Config.class);

    @Autowired
    private RedisProperties redisProperties;


    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {

        org.redisson.config.Config config = null;
        Method clusterMethod = ReflectionUtils.findMethod(RedisProperties.class, "getCluster");
        Method timeoutMethod = ReflectionUtils.findMethod(RedisProperties.class, "getTimeout");
        Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
        int timeout;
        if(null == timeoutValue){
            timeout = 10000;
        }else if (!(timeoutValue instanceof Integer)) {
            Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
            timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
        } else {
            timeout = (Integer)timeoutValue;
        }


        config = new org.redisson.config.Config();
        String prefix = "redis://";


        config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout(timeout)
                .setDatabase(redisProperties.getDatabase())
                .setPassword(redisProperties.getPassword());

        // redission默认是org.redisson.codec.FstCodec  这个加字段、减字段会出现不兼容  这里设置为 默认string
//        config.setCodec(StringCodec.INSTANCE);
        config.setCodec(JsonJacksonCodec.INSTANCE);

        logger.info("RedissonConfig Load host={},port={},database={},prefix password={},codec={}"
                ,redisProperties.getHost(),redisProperties.getPort(),redisProperties.getDatabase(),redisProperties.getPassword(),config.getCodec());

        return Redisson.create(config);


    }

}
