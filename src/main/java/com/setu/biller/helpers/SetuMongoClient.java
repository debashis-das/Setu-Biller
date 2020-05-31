package com.setu.biller.helpers;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class SetuMongoClient {

    @Autowired
    Environment environment;

    private MongoClient mongoClient;
    private MongoDatabase db;
    protected final Log logger = LogFactory.getLog(getClass());

    private void init(){
        boolean mongoAlive = true;
        if(mongoClient != null){
            try {
                mongoClient.getClusterDescription();
            } catch (Exception e) {
                mongoAlive = false;
                logger.info("Mongo connection issue");
                mongoClient.close();
            }
        }else{
            mongoAlive = false;
        }
        if(!mongoAlive){
            String username = environment.getProperty("spring.data.mongodb.username");
            String password = environment.getProperty("spring.data.mongodb.password");
            String database = environment.getProperty("spring.data.mongodb.database");
            String port = environment.getProperty("spring.data.mongodb.port");
            String host = environment.getProperty("spring.data.mongodb.host");
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        
            final MongoClientSettings clientSettings = MongoClientSettings.builder()
            .applyConnectionString(
                    new ConnectionString("mongodb://"+username+":"+password+"@"+host+":"+port+"/"+database+"?retryWrites=false"))
            .codecRegistry(codecRegistry)
            .build();
            mongoClient =  MongoClients.create(clientSettings);
            db = mongoClient.getDatabase(database);
        }
    }

    public MongoCollection getMongoCollection(String collectionName){
        init();
        return db.getCollection(collectionName);
    }
}