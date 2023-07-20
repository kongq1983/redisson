package com.kq.collection;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import java.util.Map;

public class RMapTest extends BaseTest{

    public static final String MAP_KEY = "testMapKey";


    @Before
    public void begin() throws Exception{
        System.out.println("call begin function");
//        this.flushdb();
    }

    @Test
    public void testAdd() throws Exception{
        RedissonClient redissonClient = this.redisson();
        RMap<String,String> map = redissonClient.getMap(MAP_KEY);
        map.put("one","1");
        map.put("two","2");

        for(Map.Entry<String, String> kv : map.entrySet() ) {
            String key = kv.getKey();
            String value = kv.getValue();

            System.out.printf("key=%s,value=%s \n",key,value);
        }





    }


}
