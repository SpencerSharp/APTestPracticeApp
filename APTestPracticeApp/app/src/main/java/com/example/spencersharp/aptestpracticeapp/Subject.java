package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;

@DynamoDBTable(tableName = "Subjects")
public class Subject
{
    public long id;
    public String subjectName;
    public String topicIDs;

    public Subject()
    {

    }

    public Subject(long id, String subjectName, String topicIDs)
    {
        this.id = id;
        this.subjectName = subjectName;
        this.topicIDs = topicIDs;
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
    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    @DynamoDBAttribute(attributeName = "topicIDs")
    public String getTopicIDsString()
    {
        return topicIDs;
    }

    public void setTopicIDsString(String topicIDsString)
    {
        topicIDs = topicIDsString;
    }

    @DynamoDBIgnore
    private ArrayList<Long> getTopicIDsArrayList()
    {
        ArrayList<Long> questionIDsArrayList = new ArrayList<Long>();
        String[] topicIDsArray = topicIDs.split("-");
        for(String topicID : topicIDsArray)
            questionIDsArrayList.add(Long.parseLong(topicID));
        return questionIDsArrayList;
    }

    /*
    public ArrayList<Topic> getTopicArrayList()
    {
        ArrayList<Topic> topics = new ArrayList<Topic>();
        ArrayList<Long> topicIDsArrayList = getTopicIDsArrayList();
        LocalDBHandler db = new LocalDBHandler();
        for(Long topicID : topicIDsArrayList)
        {
            Topic topic = db.getTopicFromID(topicID);
            topics.add(topic);
        }
        return topics;
    }
    */

    @DynamoDBIgnore
    public Subject clone()
    {
        Subject subject = new Subject(id, subjectName, topicIDs);
        return subject;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + subjectName + " " + topicIDs;
        return ret;
    }

    /*
    @DynamoDBAttribute(attributeName = "questionIDs")
    public String getQuestionIDsString()
    {
        return questionIDs;
    }

    public ArrayList<Long> getQuestionIDsArrayList()
    {
        ArrayList<Long> questionIDsArrayList = new ArrayList<Long>();
        String[] questionIDsArray = questionIDs.split("-");
        for(String practiceQuestionID : questionIDsArray)
            questionIDsArrayList.add(Long.parseLong(practiceQuestionID));
        return questionIDsArrayList;
    }

    public ArrayList<Question> getQuestionArrayList()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        ArrayList<Long> questionIDsArrayList = getQuestionIDsArrayList();
        LocalDBHandler db = new LocalDBHandler();
        for(Long questionID : questionIDsArrayList)
        {
            Question question = db.getQuestionFromID(questionID);
            questions.add(question);
        }
        return questions;
    }
    */
}