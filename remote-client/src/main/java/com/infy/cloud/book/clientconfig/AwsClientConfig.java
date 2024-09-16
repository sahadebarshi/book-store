package com.infy.cloud.book.clientconfig;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class AwsClientConfig {

    public static AmazonSQS getSqsClient() {
        AWSCredentials credentials = new BasicAWSCredentials(
                "",
                ""
        );

         return AmazonSQSClientBuilder
                .standard()
                .withRegion(Regions.AF_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
