package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spencersharp on 5/16/16.
 */
public class DynamoHandler
{
    DynamoDBMapper mapper;
    public DynamoHandler()
    {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIN66QOVMITPCYOHQ","XngW64gUlhcVWf8SOkrc5dojccYg8SjRENaQClhJ");
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
        mapper = new DynamoDBMapper(client);
    }

    public long getVersion()
    {
        long version = 0;

        //TODO: Write code for retrieving version from DynamoDB's "version" table you created.

        return version;
    }





    public Subject getSubjectFromID(long id)
    {
        Subject subject = mapper.load(Subject.class,id);
        return subject;
    }

    public void setSubject(Subject subject)
    {
        mapper.save(subject);
    }

    public void deleteSubject(Subject subject)
    {
        mapper.delete(subject);
    }

    public ArrayList<Subject> getSubjects()
    {
        List<Subject> subjects = new ArrayList<Subject>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        subjects = mapper.scan(Subject.class,scanExpression);


        return (ArrayList)subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects)
    {
        List<Subject> curSubjects = getSubjects();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(Subject subject : curSubjects)
        {
            boolean hasFoundSameID = false;
            for(Subject subject2 : subjects)
            {
                if(subject.getID()==subject2.getID())
                {
                    idsSaved.add(subject2.getID());
                    setSubject(subject2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteSubject(subject);
            }
        }

        for(Subject subject : subjects)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==subject.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setSubject(subject);
            }
        }
    }





    public ArrayList<Topic> getTopics()
    {
        ArrayList<Topic> topics = new ArrayList<Topic>();



        return topics;
    }

    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();



        return questions;
    }

    public ArrayList<AnswerChoice> getAnswerChoices()
    {
        ArrayList<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();



        return answerChoices;
    }





    public Student getStudentFromID(long id)
    {

    }

    public void setStudent(Student student)
    {

    }

    public ArrayList<Student> getStudents()
    {
        ArrayList<Student> student = new ArrayList<Student>();



        return student;
    }

    public void setStudents(ArrayList<Student> students)
    {

    }





    public QuestionData getQuestionDataFromID(long id)
    {

    }

    public void setQuestionData(QuestionData questionData)
    {

    }

    public ArrayList<QuestionData> getQuestionDataIDs()
    {
        ArrayList<QuestionData> questionData = new ArrayList<QuestionData>();



        return questionData;
    }

    public void setQuestionData(ArrayList<QuestionData> questionData)
    {

    }
}