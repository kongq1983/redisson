package com.kq.redission.singleserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Component
public class RedisClearKey {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void clearKey(String keyMatch){

//        try {
//            Cursor<Map.Entry<String, Set<String>>> cursor = stringRedisTemplate.scan("filed",
//                    ScanOptions.scanOptions().match(keyMatch).count(1000).build());
//            while (cursor.hasNext()) {
//                String key = cursor.next().getKey()
//                Set<String> valueSet = cursor.next().getValue();
//            }
//            //关闭scan
//            cursor.close();
//        } catch (IOException e) {
//
//        }
    }


}
