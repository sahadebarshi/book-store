package com.infy.cloud.book.db;

import com.google.inject.Singleton;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Singleton
@NoArgsConstructor
public class DBConnection extends DBConAbstract{

      public MongoClientSettings getDbConnection()
      {
            return this.getDatabaseConnection();
      }

}
