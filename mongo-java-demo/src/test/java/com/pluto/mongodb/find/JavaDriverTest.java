package com.pluto.mongodb.find;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.PushOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Projections.slice;

/**
 * Description:
 * Author: Administrator
 * Date:2018-04-01 下午 03:09
 */
public class JavaDriverTest {

    private static final Logger logger = LoggerFactory.getLogger(JavaDriverTest.class);

    private MongoDatabase db;

    private MongoCollection<Document> collection;

    @Resource
    private MongoClient client;

    @Before
    public void init() {
        db = client.getDatabase("pluto");
        collection = db.getCollection("users");
    }

    @Test
    //
    //查找lison5评语为"lison是苍老师的小迷弟"的人
    //db.user.find({"comments":{"$elemMatch":{"author":"lison5","content":"lison是苍老师的小迷弟"}}}).pretty()
    public void testElemMatch() {
        final List<Document> ret = new ArrayList<>();
        Block<Document> printBlock = getBlock(ret);

        Document filter = new Document().append("author", "lison5").append("content", "lison是苍老师的小迷弟");
        Bson elemMatch = Filters.elemMatch("comments", filter);
        FindIterable<Document> find = collection.find(elemMatch);
        printOperation(ret, printBlock, find);

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
    //新增评论时，使用$sort 运算符进行排序，插入评论后，再按照时间降序排序
    public void demoStep1() {
        Bson filter = eq("username", "lison");
        Document document = new Document().append("author", "cang").append("content", "lison是我粉丝").append("commentTime", new Date());

        Document sortDoc = new Document().append("commentTime", -1);
        PushOptions sortDocument = new PushOptions().sortDocument(sortDoc);
        Bson pushEach = Updates.pushEach("comments", Arrays.asList(document), sortDocument);
        UpdateResult updateOne = collection.updateOne(filter, pushEach);
        logger.info(updateOne.toString());
    }

    @Test
    //查看人员时加载最新的三条评论
    //db.users.find({"username":"lison"},{"comments":{"$slice":[0,3]}}).pretty()
    public void demoStep2() {
        final List<Document> ret = new ArrayList<>();
        Block<Document> printBlock = getBlock(ret);
        FindIterable<Document> find = collection.find(eq("username", "lison"))
                .projection(slice("comments", 0, 3));
        printOperation(ret, printBlock, find);
    }

    @Test
    //点击评论的下一页按钮，新加载三条评论
    //db.users.find({"username":"lison"},{"comments":{"$slice":[3,3]},"$id":1}).pretty()
    public void demeStep3(){
        final List<Document> document = new ArrayList<>();
        Block<Document> blockDoc = getBlock(document);

        Bson filter = eq("username", "lison");
        Bson slice = slice("comments", 3, 3);
        Bson include = include("id");
        Bson projection = fields(slice, include);

        FindIterable<Document> find = collection.find(filter).projection(projection);

        printOperation(document, blockDoc, find);

    }

    
    //-----------------------------------------------工具类-----------------------------------------------------------------------
    private Block<Document> getBlock(final List<Document> ret) {
        Block<Document> printBlock = new Block<Document>() {

            @Override
            public void apply(Document document) {
                logger.info("-------------");
                logger.info(document.toJson());
                logger.info("-------------");
                ret.add(document);
            }
        };
        return printBlock;
    }

    private void printOperation(List<Document> ret, Block<Document> printBlock, AggregateIterable<Document> aggregate) {
        aggregate.forEach(printBlock);
        logger.info(String.valueOf(ret.size()));
        ret.removeAll(ret);
    }

    private void printOperation(List<Document> ret, Block<Document> printBlock, FindIterable<Document> find) {
        find.forEach(printBlock);
        logger.info(String.valueOf(ret.size()));
        ret.removeAll(ret);
    }
}
