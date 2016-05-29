package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "AnswerChoices")
public class AnswerChoice
{
    public long id;
    public int ansChar;
    public String ansText;

    public AnswerChoice()
    {

    }

    public AnswerChoice(long id, int ansChar, String ansText)
    {
        this.id = id;
        this.ansChar = ansChar;
        this.ansText = ansText;
    }

    @DynamoDBHashKey(attributeName = "_id")
    public long getID()
    {
        return id;
    }

    public void setID(long id)
    {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "ansChar")
    public int getAnsChar()
    {
        return ansChar;
    }

    public void setAnsChar(int ansChar)
    {
        this.ansChar = ansChar;
    }

    @DynamoDBAttribute(attributeName = "ansText")
    public String getAnsText()
    {
        return ansText;
    }

    public void setAnsText(String ansText)
    {
        this.ansText = ansText;
    }

    @DynamoDBIgnore
    public AnswerChoice clone()
    {
        AnswerChoice answerChoice = new AnswerChoice(id, ansChar, ansText);
        return answerChoice;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + ansChar + " " + ansText;
        return ret;
    }
}
