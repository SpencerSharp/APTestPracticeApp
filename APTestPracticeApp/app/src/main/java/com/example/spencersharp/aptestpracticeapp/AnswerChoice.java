package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "AnswerChoices")
public class AnswerChoice
{
    public long id;
    public char ansChar;
    public String ansText;

    public AnswerChoice()
    {

    }

    public AnswerChoice(long id, char ansChar, String ansText)
    {
        this.id = id;
        this.ansChar = ansChar;
        this.ansText = ansText;
    }

    @DynamoDBAttribute(attributeName = "id")
    public long getID()
    {
        return id;
    }

    public void setID(long id)
    {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "ansChar")
    public char getAnsChar()
    {
        return ansChar;
    }

    public void setAnsChar(char ansChar)
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
}
