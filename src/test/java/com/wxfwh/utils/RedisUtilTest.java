package com.wxfwh.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisUtilTest {

    @Test
    public void testRedis(){
        Jedis jedis=RedisUtil.getJedis();
        System.out.println(jedis.get("test_redis"));
        jedis.set("test_redis","22333");
        System.out.println(jedis.get("test_redis"));
    }

}