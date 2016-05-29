package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.Arrays;

@DynamoDBTable(tableName = "Topics")
public class Topic
{
    public long id;
    public String topicName;
    public String questionIDs;

    public Topic()
    {

    }

    public Topic(long id, String topicName, String questionIDsString)
    {
        this.id = id;
        this.topicName = topicName;
        questionIDs = questionIDsString;
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

    @DynamoDBAttribute(attributeName = "subjectName")
    public String getTopicName()
    {
        return topicName;
    }

    public void setTopicName(String topicName)
    {
        this.topicName = topicName;
    }

    @DynamoDBAttribute(attributeName = "questionIDs")
    public String getQuestionIDsString()
    {
        return questionIDs;
    }

    public void setQuestionIDsString(String questionIDsString)
    {
        questionIDs = questionIDsString;
    }

    @DynamoDBIgnore
    public ArrayList<Long> getQuestionIDsArrayList()
    {
        String[] questionIDsArray = questionIDs.split("-");
        ArrayList<Long> questionIDsArrayList = (ArrayList) Arrays.asList(questionIDsArray);
        return questionIDsArrayList;
    }

    /*
    public ArrayList<Question> getQuestions()
    {
        LocalDBHandler localDB = new LocalDBHandler();

        ArrayList<Long> questionIDsArrayList = getQuestionIDsArrayList();
        ArrayList<Question> questions = new ArrayList<Question>();

        for(Long questionID: questionIDsArrayList)
            questions.add(localDB.getQuestionFromID(questionID));

        return questions;
    }
    */

    @DynamoDBIgnore
    public Topic clone()
    {
        Topic topic = new Topic(id, topicName, questionIDs);
        return topic;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + topicName + " " + questionIDs;
        return ret;
    }
}