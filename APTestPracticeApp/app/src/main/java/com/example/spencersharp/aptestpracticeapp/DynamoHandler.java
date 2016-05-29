package com.example.spencersharp.aptestpracticeapp;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
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
        AWSCredentials credentials = new BasicAWSCredentials(""+R.string.db_accessKey,""+R.string.db_accessKey);
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(credentials);
        client.setEndpoint("dynamodb.us-west-2.amazonaws.com");
        mapper = new DynamoDBMapper(client);
    }

    public void incrementVersion()
    {
        Version version = new Version();
        try{
            version = mapper.load(Version.class,0);
            version.increment();
        }catch(Exception e)
        {
            version.setVersion(0);
        }
        mapper.save(version);
    }

    public long getVersion()
    {
        Version version = mapper.load(Version.class,0);

        return version.getVersion();
    }





    //Subject methods
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
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Subject> scanSubjects = mapper.scan(Subject.class,scanExpression);
        for(Subject subject : scanSubjects)
        {
            subjects.add(subject.clone());
        }

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





    //Topic Methods
    public Topic getTopicFromID(long id)
    {
        Topic topic = mapper.load(Topic.class,id);
        return topic;
    }

    public void setTopic(Topic topic)
    {
        mapper.save(topic);
    }

    public void deleteTopic(Topic topic)
    {
        mapper.delete(topic);
    }

    public ArrayList<Topic> getTopics()
    {
        ArrayList<Topic> topics = new ArrayList<Topic>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Topic> scanTopics = mapper.scan(Topic.class,scanExpression);
        for(Topic topic : scanTopics)
        {
            topics.add(topic.clone());
        }

        return (ArrayList)topics;
    }

    public void setTopics(ArrayList<Topic> topics)
    {
        List<Topic> curTopics = getTopics();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(Topic topic : curTopics)
        {
            boolean hasFoundSameID = false;
            for(Topic topic2 : topics)
            {
                if(topic.getID()==topic2.getID())
                {
                    idsSaved.add(topic2.getID());
                    setTopic(topic2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteTopic(topic);
            }
        }

        for(Topic topic : topics)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==topic.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setTopic(topic);
            }
        }
    }





    //Question methods
    public Question getQuestionFromID(long id)
    {
        Question question = mapper.load(Question.class,id);
        return question;
    }

    public void setQuestion(Question question)
    {
        mapper.save(question);
    }

    public void deleteQuestion(Question question)
    {
        mapper.delete(question);
    }

    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Question> scanQuestions = mapper.scan(Question.class,scanExpression);
        for(Question question : scanQuestions)
        {
            questions.add(question.clone());
        }

        return (ArrayList)questions;
    }

    public void setQuestions(ArrayList<Question> questions)
    {
        List<Question> curQuestions = getQuestions();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(Question question : curQuestions)
        {
            boolean hasFoundSameID = false;
            for(Question question2 : questions)
            {
                if(question.getID()==question2.getID())
                {
                    idsSaved.add(question2.getID());
                    setQuestion(question2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteQuestion(question);
            }
        }

        for(Question question : questions)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==question.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setQuestion(question);
            }
        }
    }





    //AnswerChoice Methods
    public AnswerChoice getAnswerChoiceFromID(long id)
    {
        AnswerChoice answerChoice = mapper.load(AnswerChoice.class,id);
        return answerChoice;
    }

    public void setAnswerChoice(AnswerChoice answerChoice)
    {
        mapper.save(answerChoice);
    }

    public void deleteAnswerChoice(AnswerChoice answerChoice)
    {
        mapper.delete(answerChoice);
    }

    public ArrayList<AnswerChoice> getAnswerChoices()
    {
        ArrayList<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<AnswerChoice> scanAnswerChoices = mapper.scan(AnswerChoice.class,scanExpression);
        for(AnswerChoice answerChoice : scanAnswerChoices)
        {
            answerChoices.add(answerChoice.clone());
        }

        return (ArrayList)answerChoices;
    }

    public void setAnswerChoices(ArrayList<AnswerChoice> answerChoices)
    {
        List<AnswerChoice> curAnswerChoices = getAnswerChoices();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(AnswerChoice answerChoice : curAnswerChoices)
        {
            boolean hasFoundSameID = false;
            for(AnswerChoice answerChoice2 : answerChoices)
            {
                if(answerChoice.getID()==answerChoice2.getID())
                {
                    idsSaved.add(answerChoice2.getID());
                    setAnswerChoice(answerChoice2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteAnswerChoice(answerChoice);
            }
        }

        for(AnswerChoice answerChoice : answerChoices)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==answerChoice.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setAnswerChoice(answerChoice);
            }
        }
    }





    //Student methods
    public Student getStudentFromID(long id)
    {
        Student student = mapper.load(Student.class,id);
        return student;
    }

    public void setStudent(Student student)
    {
        mapper.save(student);
    }

    public void deleteStudent(Student student)
    {
        mapper.delete(student);
    }

    public ArrayList<Student> getStudents()
    {
        ArrayList<Student> students = new ArrayList<Student>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Student> scanStudents = mapper.scan(Student.class,scanExpression);
        for(Student student : scanStudents)
        {
            students.add(student.clone());
        }

        return (ArrayList)students;
    }

    public void setStudents(ArrayList<Student> students)
    {
        List<Student> curStudents = getStudents();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(Student student : curStudents)
        {
            boolean hasFoundSameID = false;
            for(Student student2 : students)
            {
                if(student.getID()==student2.getID())
                {
                    idsSaved.add(student2.getID());
                    setStudent(student2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteStudent(student);
            }
        }

        for(Student student : students)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==student.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setStudent(student);
            }
        }
    }





    //QuestionData methods
    public QuestionData getQuestionDataFromID(long id)
    {
        QuestionData questionData = mapper.load(QuestionData.class,id);
        return questionData;
    }

    public void setQuestionData(QuestionData questionData)
    {
        mapper.save(questionData);
    }

    public void deleteQuestionData(QuestionData questionData)
    {
        mapper.delete(questionData);
    }

    public ArrayList<QuestionData> getQuestionData()
    {
        ArrayList<QuestionData> questionDataList = new ArrayList<QuestionData>();
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<QuestionData> scanQuestionData = mapper.scan(QuestionData.class,scanExpression);
        for(QuestionData questionData : scanQuestionData)
        {
            questionDataList.add(questionData.clone());
        }

        return (ArrayList)questionDataList;
    }

    public void setQuestionData(ArrayList<QuestionData> questionDataList)
    {
        List<QuestionData> curQuestionData = getQuestionData();
        ArrayList<Long> idsSaved = new ArrayList<Long>();
        for(QuestionData questionData : curQuestionData)
        {
            boolean hasFoundSameID = false;
            for(QuestionData questionData2 : questionDataList)
            {
                if(questionData.getID()==questionData2.getID())
                {
                    idsSaved.add(questionData2.getID());
                    setQuestionData(questionData2);
                    hasFoundSameID = true;
                    break;
                }
            }
            if(!hasFoundSameID)
            {
                deleteQuestionData(questionData);
            }
        }

        for(QuestionData questionData : questionDataList)
        {
            boolean hasIDbeenSaved = false;
            for(Long id : idsSaved)
            {
                if(id==questionData.getID())
                {
                    hasIDbeenSaved = true;
                    break;
                }
            }
            if(!hasIDbeenSaved)
            {
                setQuestionData(questionData);
            }
        }
    }
}