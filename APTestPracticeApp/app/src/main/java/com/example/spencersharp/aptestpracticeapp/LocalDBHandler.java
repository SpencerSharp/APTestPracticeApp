package com.example.spencersharp.aptestpracticeapp;

/**
 * Created by spencersharp on 5/27/16.
 */
public class LocalDBHandler
{
    /*Ideas:
    LocalDB is versioned, copies from the DynamoDB on launch if version isnt the same.
    LocalDB is for everything except users
    All reading of questions, subjects, server info basically, is done via LocalDB.

    For users, a local user variable is read from the DB on login, and copied into the app.
    In the app, each time the local user variable is modified, a web connection check is performed.
    If you're connected, the local user will have its version incremented and will be written to DynamoDB
    */

    public LocalDBHandler()
    {

    }

    public void updateFromDynamoDB()
    {

    }
}