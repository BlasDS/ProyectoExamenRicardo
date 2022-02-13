package com.examen;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtility {

    private static MongoClient mongoClient;
    private static MongoDatabase mongoDatabase;

    public static MongoDatabase getMongoDatabase() {
        if (mongoClient == null) {
            mongoClient = new MongoClient("localhost");
            mongoDatabase = mongoClient.getDatabase("blas");
        }
        return mongoDatabase;
    }

    public static void closeMongoClient() {
        if (mongoClient != null) {mongoClient.close();}
    }
}
