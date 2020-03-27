package com.kq.redission.component;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockComponent {

    protected Logger logger = LoggerFactory.getLogger(LockComponent.class);

    public static final String KEY = "lock:key";


    @Autowired
    private RedissonClient redissonClient;

    public void lockByTime(){

        RLock lock = null;
        try {
            logger.info(Thread.currentThread().getName()+"开始尝试获取锁");
            lock = redissonClient.getLock(KEY);
            lock.lock(5, TimeUnit.SECONDS);

            logger.info(Thread.currentThread().getName()+"得到锁 休息 7s");

            try {
                TimeUnit.SECONDS.sleep(7);
            } catch (Exception e) {
                logger.error("报错1",e);
            }
        } catch (Exception e) {
            logger.error("报错2",e);
        }
        finally {
            lock.unlock();
            logger.info(Thread.currentThread().getName()+"释放锁");
        }


    }


    public void start(){





    }




}
