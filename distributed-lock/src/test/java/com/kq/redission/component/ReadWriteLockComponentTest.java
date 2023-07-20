package com.kq.redission.component;

import com.kq.redission.dto.ShoppingCart;

import java.util.concurrent.TimeUnit;

/**
 * @author kq
 * @date 2021-03-26 14:14
 * @since 2020-0630
 */
public class ReadWriteLockComponentTest {


    public static void main0(String[] args) throws Exception {

        ReadWriteLockComponent component = new ReadWriteLockComponent();

        String inventoryId = "orange";
        int size = 1;

        component.addShoppingCartDetail(inventoryId, size);


        Runnable readRunnable = () -> {
            ShoppingCart cart = component.getShoppingCartDetail(inventoryId, size);
            System.out.println(Thread.currentThread().getName() + ",get data =" + cart);
        };


        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(readRunnable, "thread-" + i);
            thread.start();
        }


        TimeUnit.MINUTES.sleep(30);

    }


    public static void main2(String[] args) throws Exception {

        ReadWriteLockComponent component = new ReadWriteLockComponent();

        String inventoryId = "orange";
        int size = 1;

        Runnable readRunnable0 = () -> {
            component.getShoppingCartDetailSleep(inventoryId, size, 15);
        };
        Thread rthread = new Thread(readRunnable0, "read-thread-0");
        rthread.start();


        Runnable writeRunnable = () -> {
            component.addShoppingCartDetail(inventoryId, size);
        };

        Thread wthread = new Thread(writeRunnable, "write-thread-0");
        wthread.start();


        Runnable readRunnable = () -> {
            ShoppingCart cart = component.getShoppingCartDetail(inventoryId, size);
            System.out.println(Thread.currentThread().getName() + ",get data =" + cart);
        };


        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(readRunnable, "thread-" + i);
            thread.start();
        }


        TimeUnit.MINUTES.sleep(30);

    }

    public static void main(String[] args) throws Exception {

        ReadWriteLockComponent component = new ReadWriteLockComponent();
        component.addShoppingCartDetail("aaa",1);

    }


}
