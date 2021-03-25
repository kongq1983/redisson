package com.kq.shopping.cart.controller;

import com.kq.shopping.cart.dto.ShoppingCartDetail;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Date;

/**
 * @author kq
 * @date 2021-03-25 16:28
 * @since 2020-0630
 */
@RestController
@RequestMapping("/shopping/cart")
public class ShoppingCartController {

    protected Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private RedissonClient redissonClient;

    private AtomicLong ato = new AtomicLong();
    // http://localhost:10003/shopping/cart/add/admin/yu
    @RequestMapping("/add/{userId}/{inventoryId}")
    public String  shoppingCartAdd(@PathVariable("userId") String userId, @PathVariable("inventoryId")String inventoryId){

        RMap<String, ShoppingCartDetail> shoppingCartDetailRMap = redissonClient.getMap("shopping:cart:"+userId);
        int size = 1;

        RLock keyLock = shoppingCartDetailRMap.getLock("lock:"+inventoryId);

        keyLock.lock();
        try {

            ShoppingCartDetail detail = shoppingCartDetailRMap.get(inventoryId);
            Date date = new Date();
            if(detail==null){
                detail = new ShoppingCartDetail();
                detail.setInventoryId(inventoryId);
                detail.setSize(size);
                detail.setCreateTime(date);
                detail.setUpdateTime(date);
            }else {
                detail.setSize(detail.getSize()+size);
                detail.setUpdateTime(date);
            }

            shoppingCartDetailRMap.put(detail.getInventoryId(),detail);
        } finally {
            keyLock.unlock();
        }

        return "ok";
    }


    @RequestMapping("/minus/{userId}/{inventoryId}")
    public String  shoppingCartMinus(@PathVariable("userId")String userId,@PathVariable("inventoryId")String inventoryId){

        RMap<String, ShoppingCartDetail> shoppingCartDetailRMap = redissonClient.getMap("shopping:cart:"+userId);
        int size = 1;

        RLock keyLock = shoppingCartDetailRMap.getLock("lock:"+inventoryId);
        keyLock.lock();
        try {
            Date date = new Date();
            ShoppingCartDetail detail = shoppingCartDetailRMap.get(inventoryId);

            if(detail==null){
                detail = new ShoppingCartDetail();
                detail.setInventoryId(inventoryId);
                detail.setSize(0);
                detail.setCreateTime(date);
                detail.setUpdateTime(date);
            }else {
                int newSize = detail.getSize()-size;
                detail.setSize(newSize>=0?newSize:0);
                detail.setUpdateTime(date);
            }

            shoppingCartDetailRMap.put(detail.getInventoryId(),detail);
        } finally {
            keyLock.unlock();
        }

        return "ok";
    }

    //http://localhost:10003/shopping/cart/list/admin
    @RequestMapping("/list/{userId}")
    public Collection<ShoppingCartDetail> shoppingCartList(@PathVariable("userId")String userId){

        RMap<String, ShoppingCartDetail> shoppingCartDetailRMap = redissonClient.getMap("shopping:cart:"+userId);

        Collection<ShoppingCartDetail> result = Collections.emptyList();
        try {
            result = shoppingCartDetailRMap.readAllValues();
        }catch (RedisException e) {
            logger.error("redis报错",e);
            shoppingCartDetailRMap.delete();
            logger.warn("删除购物车 key={}","shopping:cart:"+userId);
        }

        return result;
    }




}
