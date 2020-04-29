package com.kq.redission.singleserver.controller;


import com.kq.redission.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/redis/get")
    public String get(@RequestParam("key")String key) {

        String value = redisService.getValue(key);


        return value;

    }


    @RequestMapping("/redis/set")
    public String get(@RequestParam("key")String key,@RequestParam String value) {

        try{
            redisService.setStringValue(key,value);
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }


    @RequestMapping("/redis/haskey")
    public String haskey(@RequestParam("key")String key) {

        try{
            return String.valueOf(stringRedisTemplate.hasKey(key));
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }


    }

}
