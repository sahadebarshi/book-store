package com.infy.cloud.book.serviceImpl;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.infy.cloud.book.clientconfig.AwsClientConfig;
import com.infy.cloud.book.common.util.CustomGson;
import com.infy.cloud.book.common.util.ResponseModel;
import com.infy.cloud.book.db.DBConnection;
import com.infy.cloud.book.model.BookDetailsDto;
import com.infy.cloud.book.model.BookOrderDto;
import com.infy.cloud.book.service.BookOrderService;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.infy.cloud.book.clientconfig.AwsClientConfig.getSqsClient;
import static com.mongodb.client.model.Filters.eq;

@Singleton
@Slf4j
public class BookOrderServiceImpl implements BookOrderService
{
    private final String queueUrl = "https://sqs.ap-south-1.amazonaws.com/654654139776/book-orders";
    private static MongoClientSettings mongoClientSettings;

    @Inject
    public BookOrderServiceImpl(DBConnection dbConnection) {
        mongoClientSettings = dbConnection.getDbConnection();
    }
    @Override
    public Response postOrder(BookOrderDto bookOrderDto) {

        String orderMsg = CustomGson.getgSon().toJson(bookOrderDto);
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(this.queueUrl)
                .withMessageBody(orderMsg);

        SendMessageResult result = getSqsClient().sendMessage(sendMessageRequest);
        log.info("Order sent with SQS message id {}",result.getMessageId());


        MongoClient mongoClient = getMongoClient();
        MongoDatabase database = mongoClient.getDatabase("book-store");

            try{
                database.getCollection("Book_Orders")
                        .insertOne(Document.parse(CustomGson.getgSon().toJson(bookOrderDto)));
                log.info("Order saved...");
            }catch (MongoException e) {
                mongoClient.close();
                log.error("Connection failed with the errors" + e);
            }
           mongoClient.close();


        return  Response.ok().type(MediaType.APPLICATION_JSON)
                .entity(ResponseModel.builder()
                        .responseCode(200).msg("Order is received..").build()).build();
    }

    @Override
    public Response getOrders() {
         List<BookOrderDto> bookOrderDtoList = new ArrayList<>();
         List<Document> docs =new ArrayList<>();

        MongoClient mongoClient = getMongoClient();
        MongoCollection<Document> collection = mongoClient
                .getDatabase("book-store").getCollection("Book_Orders");
        MongoCursor<Document> cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            docs.add(cursor.next());
        }
        mongoClient.close();
        docs.stream().forEach(doc-> bookOrderDtoList
                .add(new Gson().fromJson(CustomGson.getgSon().toJson(doc), BookOrderDto.class)));

        return Response.ok().type(MediaType.APPLICATION_JSON).entity(bookOrderDtoList).build();
    }


    private static MongoClient getMongoClient()
    {
        return MongoClients.create(mongoClientSettings);
    }

}
