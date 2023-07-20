package com.kq.redisson.config;

import com.kq.redisson.util.Util;
import com.sun.xml.internal.ws.spi.db.DatabindingException;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class SingleConfigTest {

    public static void main(String[] args) throws Exception {
        // connects to 127.0.0.1:6379 by default
//        RedissonClient redisson = Redisson.create();

        long index = 0;

        AtomicLong atomicLong = new AtomicLong(1);

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + Util.REDIS_SERVER_URL).setDatabase(12)
                .setPassword("jkct-redis-123456");

        RedissonClient redisson = Redisson.create(config);



        Thread[] threads = new Thread[30];

        for(Thread thread : threads) {

            Runnable runnable = () -> {

                while (true) {
                    System.out.println("start execute time : " + getFormatDateLong());


                    RList list = redisson.getList("list:list" + atomicLong.incrementAndGet());
                    list.add("one");
//                list.add("two");

                    List<String> loadAll = list.readAll();

                    loadAll.forEach(d -> {
                        System.out.println("data=" + d);
                    });

                    try {
//                        TimeUnit.MILLISECONDS.sleep(200);
                        TimeUnit.MINUTES.sleep(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.start();
        }

        TimeUnit.HOURS.sleep(36);

    }


    public static String getFormatDateLong() {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return f.format(new Date());

    }


}
