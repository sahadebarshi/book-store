package com.infy.cloud.book.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.infy.cloud.book.common.util.CustomDateSerializer;
import lombok.*;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

//@Setter
//@Getter
@Builder
@Data
@AllArgsConstructor
//@NoArgsConstructor
@ToString
public class BookDetailsDto implements Serializable {
         @JsonProperty("bookId")
         private String _id;
         private String bookName;
         private String price;
         private String publication;
         private String author;
        // @JsonSerialize(using = CustomDateSerializer.class)
         @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
         private Date publicationDate;
         private String modifiedDate;
         private String creationDate;
         private String bookUrl;
         private String bookIcon;

        public BookDetailsDto()
         {
             if(this.get_id()==null)
             this._id = generateUUID();
         }

         private static String generateUUID()
         {
             UUID uuid=UUID.randomUUID();
             return uuid.toString();
         }
}
