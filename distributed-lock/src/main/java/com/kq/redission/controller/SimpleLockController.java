package com.kq.redission.controller;


import com.kq.redission.component.FairLockComponent;
import com.kq.redission.component.LockComponent;
import com.kq.redission.component.SemaphoreComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SimpleLockController {

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private FairLockComponent fairLockComponent;

    @Autowired
    private SemaphoreComponent semaphoreComponent;


    @RequestMapping("/redis/notLock")
    public String notLock() {

        try{
            lockComponent.notLock();
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }


    @RequestMapping("/redis/lock")
    public String lock() {

        try{
            lockComponent.lockByTime();
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }



    @RequestMapping("/redis/lockTime")
    public String lockTime() {

        try{
            lockComponent.testLockTime();
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }



    @RequestMapping("/redis/fairLock")
    public String fairLock() {

        try{
            fairLockComponent.lockByTime();
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }


    @RequestMapping("/redis/semaphore")
    public String Semaphore() {

        try{
            semaphoreComponent.execute();
        }catch (Exception e){
            return e.getMessage();
        }

        return "ok";

    }




}
