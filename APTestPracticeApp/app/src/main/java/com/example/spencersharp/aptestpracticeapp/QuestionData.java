package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "QuestionData")
public class QuestionData
{
    public long id;
    public int correctAnswerChoice;
    public int userPrevChoice;

    public QuestionData()
    {
        userPrevChoice = 0;
    }

    public QuestionData(long id, int correctAnswerChoice, int userPrevChoice)
    {
        this.id = id;
        this.correctAnswerChoice = correctAnswerChoice;
        this.userPrevChoice = userPrevChoice;
    }

    public QuestionData(Question q)
    {
        id = q.getID();
        correctAnswerChoice = q.getCorrectAnswerChoice();
        userPrevChoice = 0;
    }

    public QuestionData(Question q, int userChoice)
    {
        id = q.getID();
        correctAnswerChoice = q.getCorrectAnswerChoice();
        userPrevChoice = userChoice;
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

    @DynamoDBAttribute(attributeName = "correctAnswerChoice")
    public int getCorrectAnswerChoice()
    {
        return correctAnswerChoice;
    }

    public void setCorrectAnswerChoice(int correctAnswerChoice)
    {
        this.correctAnswerChoice = correctAnswerChoice;
    }


    @DynamoDBAttribute(attributeName = "userPrevChoice")
    public int getUserPrevChoice()
    {
        return userPrevChoice;
    }

    public void setUserPrevChoice(int userPrevChoice)
    {
        this.userPrevChoice = userPrevChoice;
    }

    @DynamoDBIgnore
    public boolean isQuestionAttempted()
    {
        if (userPrevChoice != 0)
            return true;
        return false;
    }

    /*
    public boolean isQuestionCorrect()
    {
        LocalDBHandler localDB = new LocalDBHandler();
        char correctAnswerChoice = localDB.getQuestionFromID(id).getCorrectAnswerChoice();
        if(userPrevChoice==correctAnswerChoice)
            return true;
        return false;
    }
    */

    @DynamoDBIgnore
    public QuestionData clone()
    {
        QuestionData questionData = new QuestionData(id, correctAnswerChoice, userPrevChoice);
        return questionData;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + correctAnswerChoice + " " + userPrevChoice;
        return ret;
    }
}
