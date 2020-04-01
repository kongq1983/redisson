package com.kq.distributed.collection.component;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BucketComponent {

    protected static Logger logger = LoggerFactory.getLogger(BucketComponent.class);

    @Autowired
    private RedissonClient redissonClient;

    public void set(String key,String value)  {

        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public String get(String key)  {
        RBucket<String> bucket = redissonClient.getBucket(key);
        logger.info("bucket={}",bucket);

        if(bucket!=null) {
            return bucket.get();
        }

        return null;

    }

}
