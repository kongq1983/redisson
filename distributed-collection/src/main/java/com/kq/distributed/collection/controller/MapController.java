package com.kq.distributed.collection.controller;

import com.kq.distributed.collection.component.MapComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapController {

    @Autowired
    private MapComponent mapComponent;


    /**
     * http://192.168.6.170:10005/map/putif/one
     * @param username
     * @return
     */
    @RequestMapping("/map/putif/{username}")
    public String putIfAbsent(@PathVariable("username") String username) {

        try{
           return username+":"+mapComponent.putIfAbsent(username);
        }catch (Exception e){
            return e.getMessage();
        }

    }


    /**
     * http://192.168.6.170:10005/map/put/one
     * @param username
     * @return
     */
    @RequestMapping("/map/put/{username}")
    public String put(@PathVariable("username") String username) {

        try{
            return username+":"+mapComponent.put(username);
        }catch (Exception e){
            return e.getMessage();
        }

    }


}
