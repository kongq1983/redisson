package com.kq.redission.component;

import com.kq.redission.util.DateUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ab -n 2 -c 2  http://192.168.6.170:10002/redis/fairLock
 * @See https://blog.csdn.net/zhxdick/article/details/82632249
 */
@Component
public class FairLockComponent {

    protected Logger logger = LoggerFactory.getLogger(FairLockComponent.class);

    public static final String KEY = "lock:fairLock";


    @Autowired
    private RedissonClient redissonClient;

    public void lockByTime(){

        RLock lock = null;
        int secnod = 5;
        try {
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"开始尝试获取公平锁");
            lock = redissonClient.getFairLock(KEY);
            // 锁住5s
//            lock.lock(5, TimeUnit.SECONDS);
            lock.lock();

            if(lock.isLocked()) {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"得到公平锁 休息 "+secnod+"s !");
            }else {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"未得到公平锁 =======================");
            }

            try {
                TimeUnit.SECONDS.sleep(secnod);
            } catch (Exception e) {
                logger.error("报错1",e);
            }
        } catch (Exception e) {
            logger.error("报错2",e);
        }
        finally {
            logger.info(Thread.currentThread().getName()+":finally");
            try {
                lock.unlock();
            }catch (Exception ee) {
                logger.error("===================ee",ee);
            }
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"释放公平锁");
        }


    }


    public void start(){





    }




}
