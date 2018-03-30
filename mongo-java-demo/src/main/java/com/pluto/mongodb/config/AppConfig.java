package com.pluto.mongodb.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MongoClient mongoClient() {
        MongoClientOptions options = MongoClientOptions.builder()
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .connectionsPerHost(100)
                .threadsAllowedToBlockForConnectionMultiplier(5)
                .maxWaitTime(12000).connectTimeout(10000).build();
        MongoClient client = new MongoClient(new ServerAddress("", 27022), options);
        return client;
    }
}
