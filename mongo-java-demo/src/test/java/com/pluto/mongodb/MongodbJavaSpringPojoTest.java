package com.pluto.mongodb;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

//spring Pojo的操作方式
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class MongodbJavaSpringPojoTest {
    private static final Logger logger = LoggerFactory.getLogger(MongodbJavaSpringPojoTest.class);

    @Resource
    private MongoOperations tempelate;

    @Test
    public void insertDemo(){

    }
    @Test
    public void testUpdate(){
        //update  users  set age=6 where username = 'lison'
        //update users  set favorites.movies add "小电影2 ", "小电影3" where favorites.cites  has "东莞"
    }

    @Test
    public void testFind(){
        //select * from users  where favorites.cites has "东莞"、"东京"
        //select * from users  where username like '%s%' and (contry= English or contry = USA)
    }

    @Test
    public void testDelete(){
        //delete from users where username = ‘lison’
        //delete from users where age >8 and age <25
    }
}
