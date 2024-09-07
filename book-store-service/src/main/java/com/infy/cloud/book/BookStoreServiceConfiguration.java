package com.infy.cloud.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infy.cloud.book.configuration.BookPriceConfiguration;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookStoreServiceConfiguration extends Configuration
{
    @NotNull
    @JsonProperty
    @Valid
    private BookPriceConfiguration priceMapping;
}
