package com.kq.redission.singleserver.controller;


import com.kq.redission.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;


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


}
