package com.kq.distributed.collection.controller;

import com.kq.distributed.collection.component.BucketComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BucketController {

    @Autowired
    private BucketComponent stringComponent;

    /**
     * http://192.168.6.170:10005/bucket/add/key/value
     * @return
     */
    @RequestMapping("/bucket/add/{key}/{value}")
    public String add(@PathVariable("key") String key,@PathVariable("value") String value) {

        stringComponent.set(key,value);

        return "ok";
    }



    /**
     * http://192.168.6.170:10005/bucket/get/one
     * @return
     */
    @RequestMapping("/bucket/get/{key}")
    public String remove(@PathVariable("key") String key) {

        return stringComponent.get(key);
    }


}
