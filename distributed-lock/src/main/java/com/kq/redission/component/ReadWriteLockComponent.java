package com.kq.redission.component;

import com.kq.redission.config.ConfigUtil;
import com.kq.redission.dto.ShoppingCart;
import org.redisson.api.RMap;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * @date 2021-03-26 13:44
 * @since 2020-0630
 */
@Component
public class ReadWriteLockComponent {

    protected Logger logger = LoggerFactory.getLogger(LockComponent.class);


    public static final String READ_KEY = "lock:read";
    public static final String WRITE_KEY = "lock:write";
    public static final String READ_WRITE_KEY = "lock:read:write";
    public static final String USER_ID = "rw_admin";
    public static final String MAP_KEY = "shopping:cart:"+USER_ID;


    @Autowired
    private RedissonClient redissonClient;

    public ReadWriteLockComponent(){
        try {
            redissonClient = ConfigUtil.getRedissonClient();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public ShoppingCart getShoppingCartDetail(String inventoryId, int size){

        RMap<String, ShoppingCart> map = redissonClient.getMap(MAP_KEY);
        RReadWriteLock rwLock = map.getReadWriteLock(inventoryId);
        rwLock.readLock().lock();
        logger.info("%s is 获得读锁",Thread.currentThread().getName());

        try {
            ShoppingCart v = map.get(inventoryId);
            return v;
            // process value ...
        } finally {
            rwLock.readLock().unlock();
            logger.info("%s is 释放读锁",Thread.currentThread().getName());
        }

    }


    public ShoppingCart getShoppingCartDetailSleep(String inventoryId, int size,int timeout){

        RMap<String, ShoppingCart> map = redissonClient.getMap(MAP_KEY);
        RReadWriteLock rwLock = map.getReadWriteLock(inventoryId);
        rwLock.readLock().lock();
        logger.info("%s is 获得读锁",Thread.currentThread().getName());

        try {
            ShoppingCart v = map.get(inventoryId);

            if(timeout>0){
                TimeUnit.SECONDS.sleep(timeout);
            }

            return v;
            // process value ...
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
            logger.info("%s is 释放读锁",Thread.currentThread().getName());
        }

        return null;
    }


    public String addShoppingCartDetail(String inventoryId, int size){

        RMap<String, ShoppingCart> map = redissonClient.getMap(MAP_KEY);
        RReadWriteLock rwLock = map.getReadWriteLock(inventoryId);
        rwLock.writeLock().lock();
        logger.info("%s is 获得写锁",Thread.currentThread().getName());
        try {
            ShoppingCart v = map.get(inventoryId);

            if(v==null){
                v = new ShoppingCart();
                v.setInventoryId(inventoryId);
                v.setSize(size);
            } else {
                v.setSize(v.getSize()+size);
            }

            map.put(inventoryId,v);


            TimeUnit.SECONDS.sleep(1);
            // process value ...
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
            logger.info("%s is 释放写锁",Thread.currentThread().getName());
        }

        return "ok";

    }




}
