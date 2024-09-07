package com.infy.cloud.book.serviceImpl;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.infy.cloud.book.common.util.CustomGson;
import com.infy.cloud.book.common.util.ResponseModel;
import com.infy.cloud.book.db.DBConnection;
import com.infy.cloud.book.model.BookDetailsDto;
import com.infy.cloud.book.service.BookService;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static jakarta.ws.rs.core.Response.ok;

@Singleton
@Slf4j
public class BookServiceImpl implements BookService
{
    private static MongoClientSettings mongoClientSettings;

    @Inject
    public BookServiceImpl(DBConnection dbConnection) {
        mongoClientSettings = dbConnection.getDbConnection();
    }

    @Override
    public Response getBookDetails(String bookId) {

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);
        MongoDatabase database = mongoClient.getDatabase("book-store");
        Document document = database.getCollection("Book_Details").find(eq("_id",bookId)).first();
        mongoClient.close();
        if(document != null)
        {
            return Response.ok().type(MediaType.APPLICATION_JSON)
                    .entity(new Gson().fromJson(CustomGson.getgSon().toJson(document), BookDetailsDto.class))
                    .build();
        }
        else
        {
            return null;
        }

    }

    @Override
    public Response getAllBookDetails() {
        List<Document> docs =new ArrayList<>();
        List<BookDetailsDto> booksDetails =new ArrayList<>();
        MongoClient mongoClient = getMongoClient();
        MongoCollection<Document> collection = mongoClient
                .getDatabase("book-store").getCollection("Book_Details");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
          docs.add(cursor.next());
        }
        mongoClient.close();
        docs.stream().forEach(doc-> booksDetails
                .add(new Gson().fromJson(CustomGson.getgSon().toJson(doc), BookDetailsDto.class)));

        return Response.ok().type(MediaType.APPLICATION_JSON).entity(booksDetails).build();
    }

    @Override
    public Response removeBook(String bookId) {
        MongoClient mongoClient = getMongoClient();
       long count = mongoClient.getDatabase("book-store")
                .getCollection("Book_Details").deleteOne(Filters.eq("_id", bookId)).getDeletedCount();
        mongoClient.close();
        return   count == 1L ? Response.ok().type(MediaType.APPLICATION_JSON)
                .entity(ResponseModel.builder()
                .responseCode(200).msg("Record deleted...").build()).build() :
                 Response.ok().type(MediaType.APPLICATION_JSON)
                        .entity(ResponseModel.builder()
                                .responseCode(200).msg("Record not found").build()).build();
    }

    @Override
    public Response updateOrSave(BookDetailsDto bookDetailsDto) {
        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("book-store");
        Document document = database.getCollection("Book_Details")
                .find(eq("_id",bookDetailsDto.get_id())).first();
        int flag =0;
        if(null==document || document.isEmpty())
        {
            try{
                bookDetailsDto.setCreationDate(dateFormat());
                bookDetailsDto.setModifiedDate(dateFormat());
                database.getCollection("Book_Details")
                        .insertOne(Document.parse(CustomGson.getgSon().toJson(bookDetailsDto)));
                log.info("Data saved...");
                flag = 1;
            }catch (MongoException e) {
                mongoClient.close();
                log.error("Connection failed with the errors" + e);
            }

        }
        else{

            try {
                bookDetailsDto.setCreationDate(document.getString("creationDate"));
                bookDetailsDto.setModifiedDate(dateFormat());

                database.getCollection("Book_Details")
                        .findOneAndUpdate(Filters.eq("_id", bookDetailsDto.get_id()),
                                new Document().append("$set", constructDbObject(bookDetailsDto)));
                log.info("Data updated...");
            }
            catch (MongoException e) {
                mongoClient.close();
                log.error("Document update failed with the error: " + e);
            }
           log.info("Document update..");
        }
        mongoClient.close();
        return flag == 1 ? Response.ok().type(MediaType.APPLICATION_JSON)
                .entity(ResponseModel.builder()
                        .responseCode(200).msg("Document saved...").build()).build() :
                Response.ok().type(MediaType.APPLICATION_JSON)
                        .entity(ResponseModel.builder()
                                .responseCode(200).msg("Document updated..").build()).build();
    }

    @Override
    public void saveBookDetails(MongoClientSettings mongoClientSettings, BookDetailsDto bookDetailsDto) {

        try (MongoClient mongoClient = MongoClients.create(mongoClientSettings)) {
            try {
                bookDetailsDto.setCreationDate(dateFormat());
                bookDetailsDto.setModifiedDate(dateFormat());
                MongoDatabase database = mongoClient.getDatabase("book-store");
                database.getCollection("Book_Details")
                        .insertOne(Document.parse(CustomGson.getgSon().toJson(bookDetailsDto)));
                log.info("Data saved...");
                mongoClient.close();
            } catch (MongoException e) {
                log.error("Connection error with the errors" + e);
            }

        }

    }

    private static MongoClient getMongoClient()
    {
        return MongoClients.create(mongoClientSettings);
    } //Need to place common
    private static String dateFormat()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

    private static BasicDBObject constructDbObject(BookDetailsDto bookDetailsDto)
    {
        BasicDBObject dbObject = new BasicDBObject();
        Document doc = Document.parse(CustomGson.getgSon().toJson(bookDetailsDto));
        doc.entrySet().stream().filter(p->!("_id".equalsIgnoreCase(p.getKey()))
        ).forEach(r-> dbObject.append(r.getKey(),r.getValue().toString()));
      return dbObject;
    }
}
