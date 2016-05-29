package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "QuestionData")
public class QuestionData
{
    public long id;
    public char correctAnswerChoice;
    public char userPrevChoice;

    public QuestionData()
    {
        userPrevChoice = 0;
    }

    public QuestionData(Question q)
    {
        id = q.getID();
        correctAnswerChoice = q.getCorrectAnswerChoice();
        userPrevChoice = 0;
    }

    public QuestionData(Question q, char userChoice)
    {
        id = q.getID();
        correctAnswerChoice = q.getCorrectAnswerChoice();
        userPrevChoice = userChoice;
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

    @DynamoDBAttribute(attributeName = "correctAnswerChoice")
    public char getCorrectAnswerChoice()
    {
        return correctAnswerChoice;
    }

    public void setCorrectAnswerChoice(char correctAnswerChoice)
    {
        this.correctAnswerChoice = correctAnswerChoice;
    }


    @DynamoDBAttribute(attributeName = "userPrevChoice")
    public char getUserPrevChoice()
    {
        return userPrevChoice;
    }

    public void setUserPrevChoice(char userPrevChoice)
    {
        this.userPrevChoice = userPrevChoice;
    }

    public boolean isQuestionAttempted()
    {
        if (userPrevChoice != 0)
            return true;
        return false;
    }

    public boolean isQuestionCorrect()
    {
        LocalDBHandler localDB = new LocalDBHandler();
        char correctAnswerChoice = localDB.getQuestionFromID(id).getCorrectAnswerChoice();
        if(userPrevChoice==correctAnswerChoice)
            return true;
        return false;
    }
}
