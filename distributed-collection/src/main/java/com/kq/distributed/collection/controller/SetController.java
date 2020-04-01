package com.kq.distributed.collection.controller;

import com.kq.distributed.collection.component.SetComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SetController {

    protected static Logger logger = LoggerFactory.getLogger(SetController.class);

    @Autowired
    private SetComponent setComponent;


    /**
     * http://192.168.6.170:10005/set/add/one
     * @param username
     * @return
     */
    @RequestMapping("/set/add/{username}")
    public String add(@PathVariable("username") String username) {

        try{
            setComponent.add(username);
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";
    }

    /**
     * http://192.168.6.170:10005/list/loadAll
     * @return
     */
//    @RequestMapping("/list/loadAll")
//    public List<String> list() {
//
//        try{
//            return set.loadAll();
//        }catch (Exception e){
//
//            return Collections.emptyList();
//        }
//
//    }

    /**
     * http://192.168.6.170:10005/set/remove/one
     * @param username
     * @return
     */
    @RequestMapping("/set/remove/{username}")
    public String remove(@PathVariable("username") String username) {

        try{
            setComponent.remove(username);
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";
    }


    /**
     * http://192.168.6.170:10005/set/contains/one
     * @param username
     * @return
     */
    @RequestMapping("/set/contains/{username}")
    public String contains(@PathVariable("username") String username) {

        try{
            return String.valueOf(setComponent.contains(username));
        }catch (Exception e){
            return e.getMessage();
        }

    }



}
