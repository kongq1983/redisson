package com.kq.distributed.collection.component;

import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListComponent {

    protected static Logger logger = LoggerFactory.getLogger(ListComponent.class);

    public static final String KEY = "list:key";

    @Autowired
    private RedissonClient redissonClient;

    public void add(String data) {

        RList<String> list = redissonClient.getList(KEY);
        list.add(data);

    }

    public List<String> loadAll() {

        RList<String> list = redissonClient.getList(KEY);
        return list.readAll();

    }

    public List<String> trim() {
        RList<String> list = redissonClient.getList(KEY);
        list.trim(2,5);

        return list.readAll();

    }

    public void remove(String data) {
        RList<String> list = redissonClient.getList(KEY);
        boolean result = list.remove(data);

        logger.info("data={} 删除结果={}",data,result);

    }


}
