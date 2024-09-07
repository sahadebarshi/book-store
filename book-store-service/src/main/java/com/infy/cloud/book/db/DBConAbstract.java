package com.infy.cloud.book.db;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;


public abstract class DBConAbstract {
        protected MongoClientSettings getDatabaseConnection()
        {
                String connectionString = "mongodb+srv://debarshi51:8Dd1Of0EIkFYEq22@cluster1.obxmwa4.mongodb.net/?retryWrites=true&w=majority&appName=Cluster1";
                ServerApi serverApi = ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build();

                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .serverApi(serverApi)
                        .build();
                return settings;
        }
}
