package com.infy.cloud.book.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.infy.cloud.book.model.BookOrderDto;
import com.infy.cloud.book.service.BookOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/v1")
@Timed
@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
public class BookOrderController
{
    private final BookOrderService bookOrderService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/order")
    public Response postOrder(@Valid @NotNull BookOrderDto bookOrderDto)
    {
        return bookOrderService.postOrder(bookOrderDto);
    }

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retreiveAllBookDetails()
    {
        return bookOrderService.getOrders();
    }
}
