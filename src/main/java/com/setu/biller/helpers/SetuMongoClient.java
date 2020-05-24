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
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


public class SetuMongoClient {

    private static SetuMongoClient setuMongoClient;
    private MongoClient mongoClient;
    private MongoDatabase db;
    protected final Log logger = LogFactory.getLog(getClass());

    private SetuMongoClient(){}

    public static SetuMongoClient getInstance(){
        if(setuMongoClient == null){
            setuMongoClient = new SetuMongoClient();
        }
        return setuMongoClient;
    }

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
            CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
            CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
        
            final MongoClientSettings clientSettings = MongoClientSettings.builder()
            .applyConnectionString(
                    new ConnectionString("mongodb://setu:setu@localhost:27017/?authSource=setu_biller"))
            .codecRegistry(codecRegistry)
            .build();
            mongoClient =  MongoClients.create(clientSettings);
            db = mongoClient.getDatabase("setu_biller");
        }
    }

    public MongoCollection getMongoCollection(String collectionName){
        init();
        return db.getCollection(collectionName);
    }
}