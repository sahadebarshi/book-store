package com.infy.cloud.book.service;

import com.infy.cloud.book.model.BookOrderDto;
import jakarta.ws.rs.core.Response;

public interface BookOrderService {

    public Response postOrder(BookOrderDto bookOrderDto);
    public Response getOrders();
}
