package com.kq.collection;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class RListTest extends BaseTest{

    public static final String LIST_KEY = "testListKey";

    @Before
    public void begin() throws Exception{
        System.out.println("call begin function");
        this.flushdb();
    }

    @Test
    public void testStart() throws Exception{

        RedissonClient redissonClient = this.redisson();
        System.out.println("redissonClient="+redissonClient);
        assertThat(redissonClient,notNullValue());


        RList<String> list = redissonClient.getList(LIST_KEY);

        for(int i=1;i<=10;i++) {
            // LPUSH key element [element ...]
            list.add(String.valueOf(i));
        }

        List<String> laodList = list.readAll();
        System.out.println("laodList = "+laodList);

        // LREM key count element
        list.remove("4");
        // lrange KEY 0 -1
        laodList = list.readAll();
        System.out.println("laodList = "+laodList);

        list.addBefore("5","4");

        laodList = list.readAll();
        System.out.println("laodList = "+laodList);
        // LSET key index element
        list.fastSet(3,"a");


    }


}
