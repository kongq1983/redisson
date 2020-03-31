package com.kq.distributed.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DistributedCollectionAppliaction {

    protected static Logger logger = LoggerFactory.getLogger(DistributedCollectionAppliaction.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DistributedCollectionAppliaction.class, args);

        String[] beanNames = context.getBeanDefinitionNames();

        logger.info("load beanNames size ={}",context.getBeanDefinitionCount());

    }


}
