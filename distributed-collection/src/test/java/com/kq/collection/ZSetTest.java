package com.kq.collection;

import org.junit.Before;
import org.junit.Test;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ZSetTest extends BaseTest{

    public static final String ZSET_KEY = "testZSetKey";

    @Before
    public void begin() throws Exception{
        System.out.println("call begin function");
        this.flushdb();
    }

    @Test
    public void testStart() throws Exception{

        RedissonClient redissonClient = this.redisson();
        assertThat(redissonClient,notNullValue());

//        RSortedSet<String> zset = redissonClient.getSortedSet(ZSET_KEY);
        RScoredSortedSet<String> zset = redissonClient.getScoredSortedSet(ZSET_KEY);

        // ZADD key score member
        zset.add(5,"java");
        zset.add(3,"python");
        zset.add(2,"c");
        zset.add(1,"javascript");

        // zrange key 0 -1
        Collection<String> loadAll = zset.readAll();

        loadAll.forEach(d->{
            System.out.println("data="+d);
        });

        // zrange key 0 -1 wisthscores
        Collection<ScoredEntry<String>> scoredEntries = zset.entryRange(0,-1);

        scoredEntries.forEach(d->{
            double score = d.getScore();
            String value = d.getValue();
            String str = "value=%s,score=%.2f";
            System.out.println(String.format(str,value,score));
        });



    }

}
