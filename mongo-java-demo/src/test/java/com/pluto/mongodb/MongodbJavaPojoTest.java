package com.pluto.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.pluto.mongodb.pojo.User;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MongodbJavaPojoTest {
    private static final Logger logger = LoggerFactory.getLogger(MongodbJavaPojoTest.class);

    private MongoDatabase db;

    private MongoCollection<User> doc;

    private MongoClient client;

    @Before
    public void init(){
        //编解码器的list
        List<CodecRegistry> codecRegistry = new ArrayList<>();
        //编解码器的list加入默认的编解码器结合
        codecRegistry.add(MongoClient.getDefaultCodecRegistry());
        //生成一个pojo的编解码器
        CodecRegistry providers = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        codecRegistry.add(providers);
        //通过编解码器的list生成编解码器注册中心
        CodecRegistry registry= CodecRegistries.fromRegistries(codecRegistry);
        //把编解码器注册中心放入MongoClientOptions
        MongoClientOptions build = MongoClientOptions.builder().codecRegistry(registry).build();

        ServerAddress serverAddress = new ServerAddress("47.100.113.170",27022);

        client = new MongoClient(serverAddress, build);

        db = client.getDatabase("pluto");

        doc = db.getCollection("users",User.class);
    }

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
