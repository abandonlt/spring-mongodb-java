package com.pluto.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Description:
 * Author: Administrator
 * Date:2018-03-24 下午 09:11
 */
public class MongodbJavaDocDomeTest {
    private static final Logger logger = LoggerFactory.getLogger(MongodbJavaDocDomeTest.class);

    private MongoDatabase db;

    private MongoCollection<Document> doc;

    private MongoClient client;

    @Before
    public void init(){

        client = new MongoClient("47.100.113.170",27022);
        db = client.getDatabase("pluto");
        doc = db.getCollection("users");
    }

    @Test
    public void insertDemo(){
        Document doc1 = new Document();
        doc1.append("username", "cang");
        doc1.append("country", "USA");
        doc1.append("age", 20);
        doc1.append("lenght", 1.77f);
        doc1.append("salary", new BigDecimal("6565.22"));

        Map<String, String> address1 = new HashMap<String, String>();
        address1.put("aCode", "0000");
        address1.put("add", "xxx000");
        doc1.append("address", address1);

        Map<String, Object> favorites1 = new HashMap<String, Object>();
        favorites1.put("movies", Arrays.asList("aa","bb"));
        favorites1.put("cites", Arrays.asList("东莞","东京"));
        doc1.append("favorites", favorites1);

        Document doc2  = new Document();
        doc2.append("username", "chen");
        doc2.append("country", "China");
        doc2.append("age", 30);
        doc2.append("lenght", 1.77f);
        doc2.append("salary", new BigDecimal("8888.22"));
        Map<String, String> address2 = new HashMap<>();
        address2.put("aCode", "411000");
        address2.put("add", "我的地址2");
        doc1.append("address", address2);
        Map<String, Object> favorites2 = new HashMap<>();
        favorites2.put("movies", Arrays.asList("东游记","一路向东"));
        favorites2.put("cites", Arrays.asList("珠海","东京"));
        doc2.append("favorites", favorites2);

        doc.insertMany(Arrays.asList(doc1,doc2));
    }

    @Test
    public void testUpdate(){

    }

    @Test
    public void testFind(){

    }

    @Test
    public void testDelete(){


    }
}
