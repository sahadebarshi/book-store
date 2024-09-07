package com.infy.cloud.book.common.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseModel
{
    private final String msg;
    private final int responseCode;
}
