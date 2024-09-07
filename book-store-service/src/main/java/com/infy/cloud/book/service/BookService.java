package com.infy.cloud.book.service;

import com.infy.cloud.book.model.BookDetailsDto;
import com.mongodb.MongoClientSettings;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.ws.rs.core.Response;

@OpenAPIDefinition(info = @Info(title = "BookStore API"))
public interface BookService {

    public Response getBookDetails(String bookId);
    public Response getAllBookDetails();
    public Response removeBook(String bookId);
    public Response updateOrSave(BookDetailsDto reqBookDetails);
    public void saveBookDetails(MongoClientSettings mongoClientSettings, BookDetailsDto bookDetailsDto);
}
