package com.pluto.redis.test;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pluto.redis.entity.TCountDetail;
import com.pluto.redis.utils.JedisUtils;
import com.pluto.redis.utils.SerializerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/*
 * 测试:对象序列化后存redis, 从redis取值后反序列化为JAVA对象
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SerializerTest {


    @Test
    public void demo() {
        //工具类初始化
        JedisUtils jedis = new JedisUtils("192.168.1.111", 6379, "12345678");
        //实体类TCountDetail初始化
        TCountDetail td = new TCountDetail();
        td.setId("1");
        td.setIp("127.0.0.1");
        td.setOptime(new Date());
        td.setUsername("james");
        //将键值序列化
        byte[] keyBytes = "user:1".getBytes();
        //序列化
        byte[] valueBytes = SerializerUtils.serialize(td);
        //将序列化的数据存入redis
        jedis.setSerializer(keyBytes, valueBytes);

        //从redis获取经序列化后的数据
        byte[] resultBytes = jedis.getSerializer(keyBytes);
        //反序列化,还原成对象
        TCountDetail obj = SerializerUtils.deserialize(resultBytes, TCountDetail.class);
        System.out.println("=======" + obj.getUsername());
    }
}
