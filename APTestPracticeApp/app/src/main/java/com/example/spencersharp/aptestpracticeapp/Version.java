package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Version")
public class Version
{
    long version;

    public Version()
    {

    }

    public Version(long v)
    {
        version = v;
    }

    @DynamoDBHashKey(attributeName = "version")
    public long getVersion()
    {
        return version;
    }

    public void setVersion(long v)
    {
        version = v;
    }

    @DynamoDBIgnore
    public void increment()
    {
        version++;
    }

    public String toString()
    {
        return ""+version;
    }
}