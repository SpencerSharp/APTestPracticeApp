package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIgnore;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

@DynamoDBTable(tableName = "Students")
public class Student
{
    public long id;
    public String username;
    public String password;
    public String subjectIDs;
    public String questionDataIDs;

    public Student()
    {

    }

    public Student(long id, String username, String password, String subjectIDs, String questionDataIDs)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.subjectIDs = subjectIDs;
        this.questionDataIDs = questionDataIDs;
    }

    public Student(String s)
    {
        Scanner sc = new Scanner(s);
        id = Long.parseLong(sc.next());
        username = sc.next();
        password = sc.next();
        subjectIDs = sc.next();
        questionDataIDs = sc.next();
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

    @DynamoDBAttribute(attributeName = "username")
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "subjectIDs")
    public String getSubjectIDsString()
    {
        return subjectIDs;
    }

    public void setSubjectIDsString(String subjectIDsString)
    {
        subjectIDs = subjectIDsString;
    }

    @DynamoDBIgnore
    public ArrayList<Long> getSubjectIDsArrayList()
    {
        if(subjectIDs.equals("0"))
            subjectIDs="1";
        String[] subjectIDsArray = subjectIDs.split("-");
        ArrayList<Long> subjectIDsArrayList = new ArrayList<Long>();
        for(String subjectID : subjectIDsArray)
            subjectIDsArrayList.add(Long.parseLong(subjectID));
        return subjectIDsArrayList;
    }

    @DynamoDBIgnore
    public ArrayList<Subject> getSubjects()
    {
        LocalDBHandler localDB = new LocalDBHandler();

        ArrayList<Long> subjectIDsArrayList = getSubjectIDsArrayList();
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        for(Long subjectID : subjectIDsArrayList)
            subjects.add(localDB.getSubjectFromID(subjectID));

        return subjects;
    }

    @DynamoDBAttribute(attributeName = "questionDataIDs")
    public String getQuestionDataIDsString()
    {
        return questionDataIDs;
    }

    public void setQuestionDataIDsString(String questionDataIDsString)
    {
        questionDataIDs = questionDataIDsString;
    }

    @DynamoDBIgnore
    public ArrayList<Long> getQuestionDataIDsArrayList()
    {
        String[] questionDataIDsArray = questionDataIDs.split("-");

        ArrayList<Long> questionDataIDsArrayList = (ArrayList)Arrays.asList(questionDataIDsArray);

        return questionDataIDsArrayList;
    }

    @DynamoDBIgnore
    public ArrayList<QuestionData> getQuestionData()
    {
        LocalDBHandler localDB = new LocalDBHandler();

        ArrayList<Long> questionDataIDsArrayList = getQuestionDataIDsArrayList();
        ArrayList<QuestionData> questionData = new ArrayList<QuestionData>();

        for(Long questionDataID : questionDataIDsArrayList)
            questionData.add(localDB.getQuestionDataFromID(questionDataID));

        return questionData;
    }

    /*
    @DynamoDBIgnore
    public boolean userAttemptedQuestion(long id, char answerChoiceSelected)
    {
        LocalDBHandler localDB = new LocalDBHandler();

        QuestionData questionData = new QuestionData(localDB.getQuestionFromID(id), answerChoiceSelected);

        localDB.setQuestionData(questionData);

        if(questionData.isQuestionCorrect())
            return true;
        return false;
    }
    */

    @DynamoDBIgnore
    public boolean hasUserAttemptedQuestion(Question q)
    {
        ArrayList<QuestionData> questionData = new ArrayList<QuestionData>();
        for(QuestionData qData : questionData)
        {
            if(qData.getQuestionID()==q.getID())
            {
                if(qData.getUserPrevChoice()==0)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    @DynamoDBIgnore
    public ArrayList<Question> getQuestionsInTopicNotAttempted(long topicID)
    {
        LocalDBHandler localDB = new LocalDBHandler();
        Topic topic = localDB.getTopicFromID(topicID);
        ArrayList<Question> allQuestions = localDB.getQuestions();
        ArrayList<Question> questionsInTopicAndNotAttempted = new ArrayList<Question>();
        for(Question q : allQuestions)
        {
            if(topic.hasQuestion(q) && !hasUserAttemptedQuestion(q))
                questionsInTopicAndNotAttempted.add(q);
        }
        return questionsInTopicAndNotAttempted;
    }

    @DynamoDBIgnore
    public boolean didUserGetQuestionCorrect(long questionID)
    {
        ArrayList<QuestionData> questionDatas = getQuestionData();
        QuestionData theQuestionData = new QuestionData();
        for(QuestionData questionData : questionDatas)
        {
            if(questionData.getQuestionID()==questionID)
            {
                return questionData.getCorrectAnswerChoice()==questionData.getUserPrevChoice();
            }
        }
        return false;
    }

    @DynamoDBIgnore
    public int getTotalQuestionsRightFromSubject(long subjectID)
    {
        int total = 0;
        LocalDBHandler localDB = new LocalDBHandler();
        Subject subject = localDB.getSubjectFromID(subjectID);
        ArrayList<Topic> topics = subject.getTopicArrayList();
        for(Topic topic : topics)
        {
            ArrayList<Question> questions = topic.getQuestions();
            for(Question question : questions)
            {
                if(didUserGetQuestionCorrect(question.getID()))
                    total++;
            }
        }
        return total;
    }

    @DynamoDBIgnore
    public Student clone()
    {
        Student student = new Student(id, username, password, subjectIDs, questionDataIDs);
        return student;
    }

    @DynamoDBIgnore
    public String toString()
    {
        String ret = id + " " + username + " " + password + " " + subjectIDs + " " + questionDataIDs;
        return ret;
    }
}