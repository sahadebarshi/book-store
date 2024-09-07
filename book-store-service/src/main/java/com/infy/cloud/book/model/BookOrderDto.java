package com.infy.cloud.book.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class BookOrderDto implements Serializable
{
    @JsonProperty("orderId")
    private String _id;
    private String bookName;
    private int noOfItem;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private String orderDate;
    private String deliveryAddress;

    public BookOrderDto()
    {
        if(this._id==null)
            this._id= generateUUID();
        this.orderDate = dateFormat();
    }

    private static String generateUUID()
    {
        UUID uuid=UUID.randomUUID();
        return uuid.toString();
    }

    private static String dateFormat()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }

}
