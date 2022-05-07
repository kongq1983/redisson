package com.kq.serialize;

import com.kq.serialize.dto.ProductDto;
import org.junit.Test;
import org.redisson.client.codec.StringCodec;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * @date 2022-04-13 13:42
 * @since 2020-0630
 */
public class RedisOperateTest extends RedissonSerializeApplicationBaseTest{


    @Test
    public void testString(){

        String key = "productId:100";
        String key1 = "productId:101";
        String val = "productValue:100";
        this.redissonClient.getBucket(key).set(val);

        Object loadVal = redissonClient.getBucket(key).get();
        System.out.println("testString loadVal:"+loadVal);

        this.redissonClient.getBucket(key1, StringCodec.INSTANCE).set(val,1, TimeUnit.HOURS);
    }

    @Test
    public void testSpringString(){

        String key = "productId:200";
        String val = "productValue:200";

        this.stringRedisTemplate.opsForValue().set(key,val);


        String [] keys = {"productId:100","productId:101",key};
        for(String k : keys) {
            String value = stringRedisTemplate.opsForValue().get(k);
            System.out.println("testSpringString loadVal:" + value);

            Object loadVal = redissonClient.getBucket(k).get();
            System.out.println("testString loadVal:"+loadVal);
        }

    }

    @Test
    public void testProductObject(){

        ProductDto productDto = new ProductDto();
        productDto.setId("1");
        productDto.setName("phone");
        productDto.setPhoto("http://www.baidu.com/a.jpg");
        productDto.setPrice(new BigDecimal(3000));

        redissonClient.getBucket("product:info:100").set(productDto,1,TimeUnit.HOURS);

//        redissonClient.get("product:info:100")

    }


}
