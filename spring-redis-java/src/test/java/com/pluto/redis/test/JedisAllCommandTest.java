package com.pluto.redis.test;

import java.util.ArrayList;
import java.util.List;

import com.pluto.redis.utils.JedisUtils;
import org.junit.Test;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;


/*
 * AUTHOR james
 */
public class JedisAllCommandTest {
    //基础指令封装后测试
    @Test
    public void testCommond() {
        //工具类初始化
        JedisUtils jedis = new JedisUtils("47.100.113.170", 6379, "12345678");

        for (int i = 0; i < 100; i++) {
            //设值
            jedis.set("n" + i, String.valueOf(i));
        }
        System.out.println("keys from redis return =======" + jedis.keys("*"));

    }


    //使用pipeline批量删除
    // @Test
    public void testPipelineMdel() {
        //工具类初始化
        JedisUtils jedis = new JedisUtils("192.168.1.111", 6379, "12345678");
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            keys.add("n" + i);
        }
        jedis.mdel(keys);
        System.out.println("after mdel the redis return ---------" + jedis.keys("*"));
    }


    //使用pipeline提交所有操作
    //@Test
    public void testPipelineSyncAll() {
        //工具类初始化
        Jedis jedis = new Jedis("192.168.1.111", 6379);
        jedis.auth("12345678");
        //获取pipeline对象
        Pipeline pipe = jedis.pipelined();
        pipe.set("name", "james"); //调值
        pipe.incr("age");//自增
        //将不同类型的操作命令合并提交，并将操作操作以list返回
        List<Object> list = pipe.syncAndReturnAll();
        for (Object obj : list) {
            //将操作结果打印出来
            System.out.println(obj);
        }
        //断开连接，释放资源
        jedis.disconnect();
    }


    //使用Lua操作  eval "return redis.call('get',KEYS[1])" 1 name
    // @Test
    public void testLuaScript() {
        //工具类初始化
        Jedis jedis = new Jedis("192.168.1.111", 6379);
        jedis.auth("12345678");
        jedis.set("name", "james");
        String script = "return redis.call('get',KEYS[1])";
        Object result = jedis.eval(script, 1, "name");
        //将操作结果打印出来
        System.out.println(result);
        //断开连接，释放资源
        jedis.disconnect();
    }


    //使用Lua操作  eval "return redis.call('get',KEYS[1])" 1 name
    //@Test
    public void testLuaFile() {
        //工具类初始化
        Jedis jedis = new Jedis("192.168.1.111", 6379);
        jedis.auth("12345678");


        StringBuffer scriptBuffer = new StringBuffer("");
        scriptBuffer.append("\n");
        //拼装LUA串
        scriptBuffer.append("local key = KEYS[1]\n");
        scriptBuffer.append("local limit = tonumber(ARGV[1])\n");
        scriptBuffer.append("local expire_time = ARGV[2]\n");

        scriptBuffer.append("local is_exists = redis.call('EXISTS', key)\n");
        scriptBuffer.append("if is_exists == 1 then\n");
        scriptBuffer.append("    if redis.call('INCR', key) > limit then\n");
        scriptBuffer.append("        return 0\n");
        scriptBuffer.append("    else\n");
        scriptBuffer.append("        return 1\n");
        scriptBuffer.append("    end\n");
        scriptBuffer.append("else\n");
        scriptBuffer.append("    redis.call('SET', key, 1)\n");
        scriptBuffer.append("    redis.call('EXPIRE', key, expire_time)\n");
        scriptBuffer.append("    return 1\n");
        scriptBuffer.append("end\n");
        //计算sha值
        String scriptsha = jedis.scriptLoad(scriptBuffer.toString());
        Object result = jedis.evalsha(scriptsha, 1, "rate.limit:127.0.0.1", "10", "20");
        //将操作结果打印出来
        System.out.println(result);
        //断开连接，释放资源
        jedis.disconnect();
    }

    // @Test
    public void ttt() {
        Jedis jedis = new Jedis("192.168.1.111", 6379, 2000,
                2000, false, null, null, null);
        int i = 0;
        i = 9;
        jedis.connect();
        jedis.auth("12345678");
        System.out.println("=====" + jedis.get("name"));
    }
}
