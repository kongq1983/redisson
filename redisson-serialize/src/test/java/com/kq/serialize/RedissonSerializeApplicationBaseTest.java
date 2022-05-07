package com.kq.serialize;

import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kq
 * @date 2022-04-13 11:26
 * @since 2020-0630
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RedissonSerializeApplication.class})
@WebAppConfiguration
//@Transactional
//@Rollback()
public class RedissonSerializeApplicationBaseTest {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Autowired
    protected RedissonClient redissonClient;


}
