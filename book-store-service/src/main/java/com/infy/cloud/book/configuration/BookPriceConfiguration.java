package com.infy.cloud.book.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BookPriceConfiguration
{
   @JsonProperty
   @NotNull
   @Valid
   @JsonSetter(nulls = Nulls.AS_EMPTY)
   private Map<String, String> bookPriceMapping = new HashMap<>();
}
