package com.pluto.redis.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.pluto.redis.utils.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * AUTHOR james
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class OrderListHashTest {

    @Resource
    private RedisUtil redisUtil;

    private static String userKey = "user:" + 1 + ":order"; //模块user+用户编号+订单

    //Hash与List结合的场景，用户有多笔订单，查询用户1的所有订单，每下一笔订单LPUSH一笔到user:1:order
    @Test
    public void testAdd() {
        /*
         * hmset order:1 orderId 1 money 36.6 time 2018-01-01
         * hmset order:2 orderId 2 money 38.6 time 2018-01-01
         * hmset order:3 orderId 3 money 39.6 time 2018-01-01
         */
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderId", "1");
        map.put("money", "36.6");
        map.put("time", "2018-01-03");
        redisUtil.hmset("order:1", map);

        map.put("orderId", "2");
        map.put("money", "37.8");
        map.put("time", "2018-01-01");
        redisUtil.hmset("order:2", map);

        map.put("orderId", "3");
        map.put("money", "38.3");
        map.put("time", "2018-02-01");
        redisUtil.hmset("order:3", map);

        String[] orders = {"order:1", "order:2", "order:3"};

        redisUtil.lpush(userKey, orders);
    }

    @Test
    public void testGet() {
        //查询用户1所有订单的对应的键值
        List<Object> list = redisUtil.lrange("user:1:order", 0, -1);
        Map<String, String> map = null;
        for (Object orderKey : list) {
            //根据订单的键值查出每一笔订单的详情
            map = redisUtil.hmget(orderKey.toString());
            System.out.println(map.get("orderId") + "  =====  " + map);
        }
    }
}
