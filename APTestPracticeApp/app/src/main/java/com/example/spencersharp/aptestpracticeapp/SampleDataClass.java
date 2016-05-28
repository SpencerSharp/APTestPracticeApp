package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by spencersharp on 5/18/16.
 */

@DynamoDBTable(tableName = "Books")
public class SampleDataClass
{
    private int id;

    @DynamoDBHashKey(attributeName = "_id")
    public int getID()
    {
        return id;
    }


}
