package com.infy.cloud.book.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.infy.cloud.book.common.util.ResponseModel;
import com.infy.cloud.book.configuration.BookPriceConfiguration;
import com.infy.cloud.book.db.DBConnection;
import com.infy.cloud.book.model.BookDetailsDto;
import com.infy.cloud.book.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Path("/v1")
@Timed
@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class BookServiceController {

    private final BookService bookService;
    private final BookPriceConfiguration bookPriceConfiguration;
    private final DBConnection dbConnection;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/save")
    public Response postBookDetails(@Valid @NotNull BookDetailsDto reqBookDetails)
    {
        bookService.saveBookDetails(dbConnection.getDbConnection(), reqBookDetails);
        return Response.ok().type(MediaType.APPLICATION_JSON)
                .entity(ResponseModel.builder()
                        .responseCode(200).msg("Record saved..").build()).build();
    }

    @GET
    @Path("/book/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retreiveBookDetails(@NotNull @NotEmpty @PathParam("bookId") String bookId)
    {
        return bookService.getBookDetails(bookId);
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retreiveAllBookDetails()
    {
        log.info("FETCHING ALL BOOKS....");
        return bookService.getAllBookDetails();
    }

    @DELETE
    @Path("/remove/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBook(@NotNull @NotEmpty @PathParam("bookId") String bookId)
    {
        return bookService.removeBook(bookId);
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrSave(@Valid @NotNull BookDetailsDto reqBookDetails)
    {
        return bookService.updateOrSave(reqBookDetails);
    }

}
