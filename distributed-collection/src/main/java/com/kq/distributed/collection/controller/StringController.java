package com.kq.distributed.collection.controller;

import com.kq.distributed.collection.component.StringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StringController {

    @Autowired
    private StringComponent stringComponent;

    /**
     * http://192.168.6.170:10005/string/add/one
     * @return
     */
    @RequestMapping("/string/add/{key}/{value}")
    public String add(@PathVariable("key") String key,@PathVariable("value") String value) {

        stringComponent.save(key,value);

        return "ok";
    }



    /**
     * http://192.168.6.170:10005/string/get/one
     * @return
     */
    @RequestMapping("/string/get/{key}")
    public String remove(@PathVariable("key") String key) {

        return stringComponent.getValue(key);
    }


}
