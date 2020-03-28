package com.kq.redission.component;


import com.kq.redission.util.DateUtil;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


/**
 * ab -n 2 -c 2  http://192.168.6.170:10002/redis/semaphore
 */
@Component
public class SemaphoreComponent {

    protected Logger logger = LoggerFactory.getLogger(SemaphoreComponent.class);

    public static final String KEY = "redisson:semaphore";

    @Autowired
    private RedissonClient redissonClient;

    public void execute(){

        RSemaphore semaphore = redissonClient.getSemaphore(KEY);
        semaphore.trySetPermits(5);

        try {
            // or try to acquire 10 permits or wait up to 15 seconds
            boolean res = semaphore.tryAcquire(1, 5, TimeUnit.SECONDS);

            logger.info("{} {} 获取请求权限结果={}", DateUtil.getFormatDate(),Thread.currentThread().getName(),res);

            if (res) {
                try {
                   TimeUnit.SECONDS.sleep(5);
                } catch (Exception e) {
                    logger.error("超时",e);
                }
                finally {
                    semaphore.release();
                    logger.info("{} {} 释放请求权限 ! ",DateUtil.getFormatDate(),Thread.currentThread().getName());
                }
            }
        }catch (Exception e) {
            logger.error("报错",e);
        }

    }


}
