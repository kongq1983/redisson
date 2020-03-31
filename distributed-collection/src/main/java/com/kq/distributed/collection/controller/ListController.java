package com.kq.distributed.collection.controller;

import com.kq.distributed.collection.component.ListComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ListController {

    protected static Logger logger = LoggerFactory.getLogger(ListController.class);

    @Autowired
    private ListComponent listComponent;


    /**
     * http://192.168.6.170:10005/list/add/one
     * @param username
     * @return
     */
    @RequestMapping("/list/add/{username}")
    public String add(@PathVariable("username") String username) {

        try{
            listComponent.add(username);
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";
    }

    /**
     * http://192.168.6.170:10005/list/loadAll
     * @return
     */
    @RequestMapping("/list/loadAll")
    public List<String> list() {

        try{
            return listComponent.loadAll();
        }catch (Exception e){

            return Collections.emptyList();
        }

    }

    /**
     * http://192.168.6.170:10005/list/remove/one
     * @param username
     * @return
     */
    @RequestMapping("/list/remove/{username}")
    public String remove(@PathVariable("username") String username) {

        try{
            listComponent.remove(username);
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";
    }



}
