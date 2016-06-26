package com.example.spencersharp.aptestpracticeapp;

/**
 * Created by spencersharp on 5/30/16.
 */
public class DynamoCredentials {
    private static String accessKey;
    private static String secretKey;

    public DynamoCredentials()
    {

    }

    public DynamoCredentials(String aKey, String sKey)
    {
        accessKey = aKey;
        secretKey = sKey;
    }

    public String getAccessKey()
    {
        return accessKey;
    }

    public void setAccessKey(String aKey)
    {
        accessKey = aKey;
    }

    public String getSecretKey()
    {
        return secretKey;
    }

    public void setSecretKey(String sKey)
    {
        secretKey = sKey;
    }
}
