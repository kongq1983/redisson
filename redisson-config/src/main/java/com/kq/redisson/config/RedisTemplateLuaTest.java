package com.kq.redisson.config;

/**
 * @author: kq
 * @date: 2023-07-25 17:24:18
 * @since: 1.0.0
 * @description:
 */
public class RedisTemplateLuaTest {

    final static String SET_VERSION_SCRIPT = "local curVersion = tonumber(redis.call('get',KEYS[1])); "+
            "local val = tonumber(ARGV[1]); "+
            "if curVersion == nil then "+
            "   redis.call('set',KEYS[1],val) ; " +
            "elseif curVersion < val then " +
            "   redis.call('set',KEYS[1],val) ; " +
            "end; " +
            "   curVersion = tonumber(redis.call('get',KEYS[1])); " +
            "return curVersion;"
            ;


    public static void main(String[] args) {

    }

}
