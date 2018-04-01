package com.pluto.mongodb.find;

import com.mongodb.WriteResult;
import com.pluto.mongodb.pojo.Comment;
import com.pluto.mongodb.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Update.PushOperatorBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Description:
 * Author: Administrator
 * Date:2018-04-01 下午 03:10
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SpringDriverTest {
    private static final Logger logger = LoggerFactory.getLogger(SpringDriverTest.class);

    @Resource
    private MongoOperations tempelate;

    @Test
    //db.users.find({"comments":{"$elemMatch":{"author":"lison5","content":"lison是苍老师的小迷弟"}}}).pretty()
    public void testElemMatch() {
        Query query = query(where("comments").elemMatch(where("author").is("lison5").and("content").is("lison是苍老师的小迷弟")));
        List<User> find = tempelate.find(query, User.class);
        logger.info(String.valueOf(find.size()));
    }

    /*
    db.users.updateone({"username":"lison"},{
    "$push":{
    "comments":{
        "$each":[{
            "author":"james",
            "content":"lison是个好老师",
            "commentTime":ISODate("2018-01-06T04:26:18.354Z")
        }],
        $sort:{"commentTime":-1}
    }}})
     */
    @Test
    public void demoStep1() {
        Query query = query(where("username").is("lison"));
        Comment comment = new Comment();
        comment.setAuthor("cang");
        comment.setCommentTime(new Date());
        comment.setContent("lison是苍老师的小迷弟");

        Update update = new Update();
        PushOperatorBuilder push = update.push("comments");
        push.each(comment);
        push.sort(new Sort(new Sort.Order(Direction.DESC, "commentTime")));

        System.out.println("------------------");
        WriteResult updateFirst = tempelate.updateFirst(query, update, User.class);
        System.out.println("------------------");
        logger.info(String.valueOf(updateFirst.getN()));
    }

    @Test
    //查看人员时加载最新的三条评论
    //db.users.find({"username":"lison"},{"comments":{"$slice":[0,3]}}).pretty()
    public void demoStep2(){
        Query query = query(where("username").is("lison"));
        query.fields().include("comments").slice("comments",0,3);
        System.out.println("--------------");
        List<User> user = tempelate.find(query, User.class);
        System.out.println("--------------");
        logger.info(user.toString());
    }

    @Test
    //点击评论的下一页按钮，新加载三条评论
    //db.users.find({"username":"lison"},{"comments":{"$slice":[3,3]},"$id":1}).pretty()
    public void demoStep3(){
        Query query = query(where("username").is("lison"));
        query.fields().include("comments").slice("comments", 3, 3).include("id");
        System.out.println("------------------");
        List<User> users = tempelate.find(query, User.class);
        System.out.println("------------------");
        logger.info(users.toString());
    }
}
