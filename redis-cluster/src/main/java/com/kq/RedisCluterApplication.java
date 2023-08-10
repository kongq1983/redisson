package com.kq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author: kq
 * @date: 2023-08-10 14:13:49
 * @since: 1.0.0
 * @description:
 */
@SpringBootApplication
public class RedisCluterApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(RedisCluterApplication.class, args);

    }

}
