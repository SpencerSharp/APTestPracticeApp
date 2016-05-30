package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "QuestionData")
public class QuestionData
{
    public long id;
    public long questionID;
    public int correctAnswerChoice;
    public int userPrevChoice;

    public QuestionData()
    {
        userPrevChoice = 0;
    }

    public QuestionData(long id, long questionID, int correctAnswerChoice, int userPrevChoice)
    {
        this.id = id;
        this.questionID = questionID;
        this.correctAnswerChoice = correctAnswerChoice;
        this.userPrevChoice = userPrevChoice;
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

    @DynamoDBAttribute(attributeName = "questionID")
    public long getQuestionID()
    {
        return questionID;
    }

    public void setQuestionID(long questionID)
    {
        this.questionID = questionID;
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
        QuestionData questionData = new QuestionData(id, questionID, correctAnswerChoice, userPrevChoice);
        return questionData;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + correctAnswerChoice + " " + userPrevChoice;
        return ret;
    }
}