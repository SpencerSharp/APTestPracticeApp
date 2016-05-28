package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spencersharp on 5/16/16.
 */
public class DynamoHandler implements Serializable
{
    public DynamoHandler()
    {
        AWSCredentials credentials = new BasicAWSCredentials("USER ID#","Secret Key");
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        Subject s = mapper.load(Subject.class,0);

    }
}