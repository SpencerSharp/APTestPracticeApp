package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.Arrays;

@DynamoDBTable(tableName = "Questions")
public class Question
{
    public long id;
    public String qText;
    public String answerChoiceIDs;
    public char correctAnswerChoice;

    public Question()
    {

    }

    public Question(long id, String qText, String answerChoiceIDs, char correctAnswerChoice)
    {
        this.id = id;
        this.qText = qText;
        this.answerChoiceIDs = answerChoiceIDs;
        this.correctAnswerChoice = correctAnswerChoice;
    }

    @DynamoDBHashKey(attributeName = "id")
    public long getID()
    {
        return id;
    }

    public void setID(long id)
    {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "qText")
    public String getQText()
    {
        return qText;
    }

    public void setQText(String qText)
    {
        this.qText = qText;
    }

    @DynamoDBAttribute(attributeName = "answerChoiceIDs")
    public String getAnswerChoiceIDsString()
    {
        return answerChoiceIDs;
    }

    public void setAnswerChoiceIDsString(String answerChoiceIDsString)
    {
        answerChoiceIDs = answerChoiceIDsString;
    }

    public ArrayList<Long> getAnswerChoiceIDsArrayList()
    {
        String[] answerChoiceIDsArray = answerChoiceIDs.split("-");

        ArrayList<Long> answerChoiceIDsArrayList = (ArrayList) Arrays.asList(answerChoiceIDsArray);

        return answerChoiceIDsArrayList;
    }

    public ArrayList<AnswerChoice> getAnswerChoices()
    {
        LocalDBHandler localDB = new LocalDBHandler();

        ArrayList<Long> answerChoiceIDsArrayList = getAnswerChoiceIDsArrayList();
        ArrayList<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();

        for(Long answerChoiceID : answerChoiceIDsArrayList)
            answerChoices.add(localDB.getAnswerChoiceFromID(answerChoiceID));

        return answerChoices;
    }

    @DynamoDBAttribute(attributeName = "correctAnswerChoice")
    public char getCorrectAnswerChoice()
    {
        return correctAnswerChoice;
    }

    public void setCorrectAnswerChoice(char correctAnswerChoice)
    {
        this.correctAnswerChoice = correctAnswerChoice;
    }
}