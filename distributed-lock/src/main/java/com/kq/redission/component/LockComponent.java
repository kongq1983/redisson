package com.kq.redission.component;

import com.kq.redission.util.DateUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * ab -n 2 -c 2  http://192.168.6.170:10002/redis/lock
 */
//@Scope("prototype")
@Component
public class LockComponent {

    protected Logger logger = LoggerFactory.getLogger(LockComponent.class);

    public static final String KEY = "lock:key";
    public static final String LOCK_TIME_KEY = "lock:keyTime";


    @Autowired
    private RedissonClient redissonClient;


    public void notLock() {

        logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" enter notLock+++++++++++++++++++++++++++++");

        try {
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e) {
            logger.error("notLock",e);
        }

//        for(int i=0;i<Integer.MAX_VALUE;i++) {
//            int j = i;
//        }

        logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" leave notLock+++++++++++++++++++++++++++++");

    }

    public void lockByTime(){

        logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" lock+++++++++++++++++++++++++++++");

        RLock lock = null;
        int secnod = 5;
        try {
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"开始尝试获取锁");
            lock = redissonClient.getLock(KEY);

            logger.info(Thread.currentThread().getName()+"after getLock ===============================");

            // 锁住5s
//            lock.lock(5, TimeUnit.SECONDS);
            lock.lock();

            if(lock.isLocked()) {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"得到锁 休息 "+secnod+"s !");
            }else {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"未得到锁 =======================");
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
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"释放锁");
        }


    }


    /**
     * ab -n 1 -c 1  http://192.168.6.170:10002/redis/lockTime
     */
    public void testLockTime(){

        logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" testLockTime+++++++++++++++++++++++++++++");

        RLock lock = null;
        int secnod = 5;
        boolean isLock = false;
        try {
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"开始尝试获取锁");
            lock = redissonClient.getLock(LOCK_TIME_KEY);
            // 锁住5s
//            lock.lock(5, TimeUnit.SECONDS);
//            lock.lock();
//            lock.tryLock(20,TimeUnit.SECONDS);
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" 将要获取锁 !!!!!!!!!!!!!!!!!!!!!");
            // 等3s  持有20s
            isLock = lock.tryLock(3,20,TimeUnit.SECONDS);
            logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+" after tryLock -----------------------");

            if(lock.isLocked()) {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"得到锁 休息 "+secnod+"s ! isLock="+isLock);
            }else {
                logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"未得到锁 ======================= isLock="+isLock);
            }

            int lockSize = 0;

            for(int i=0;i<lockSize;i++) {
                lock.lock();

            }

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                logger.error("报错1",e);
            }


            for(int i=0;i<lockSize;i++) {
                lock.unlock();
            }

        } catch (Exception e) {
            logger.error("报错2",e);
        }
        finally {
            logger.info(Thread.currentThread().getName()+":finally");
            try {
                if(isLock){
                    lock.unlock();
                    logger.info(DateUtil.getFormatDate()+","+Thread.currentThread().getName()+"释放锁");
                }
            }catch (Exception ee) {
                logger.error("===================ee",ee);
            }

        }



    }




}
