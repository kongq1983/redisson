package com.kq.redission;

import com.kq.redission.singleserver.RedissionSingleServerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RKeysAsync;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RedissionSingleServerApplication.class})
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Test
    public void testInsert(){

        stringRedisTemplate.opsForValue().set("abc:1","1");
        stringRedisTemplate.opsForValue().set("abc:2","2");
        stringRedisTemplate.opsForValue().set("abc:11","2");
        stringRedisTemplate.opsForValue().set("abc:22","2");
        stringRedisTemplate.opsForValue().set("abc","2");
        stringRedisTemplate.opsForValue().set("bbc:22","2");
        stringRedisTemplate.opsForValue().set("cbc:22","2");

    }

    @Test
    public void testDeleteMatch() {
//        stringRedisTemplate.delete("abc*");
//        Set<String> keys = this.stringRedisTemplate.keys("abc*");
//        System.out.println("keys="+keys);


        long delSize = redissonClient.getKeys().deleteByPattern("abc:*");
        System.out.println("delSize="+delSize);

    }

    @Test
    public void testScan(){



//        RKeysAsync

    }

    @Test
    public void testIncr(){
        long code = stringRedisTemplate.opsForValue().increment("aaa:bbb:ccc");
        System.out.println("code="+code);
    }

}
