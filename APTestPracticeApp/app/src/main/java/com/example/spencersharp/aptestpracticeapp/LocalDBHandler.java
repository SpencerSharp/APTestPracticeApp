package com.example.spencersharp.aptestpracticeapp;


import java.util.ArrayList;

public class LocalDBHandler
{
    /*Ideas:
    LocalDB is versioned, copies from the DynamoDB on launch if version isnt the same.
    LocalDB is for everything except users
    All reading of questions, subjects, server info basically, is done via LocalDB.

    For users, a local user variable is read from the DB on login, and copied into the app.
    In the app, each time the local user variable is modified, a web connection check is performed.
    If you're connected, the local user will have its version incremented and will be written to DynamoDB
    */
    private long version;

    private static final String DB_NAME                           = "APTestPracticeLocalDB";

    private static final String TABLE_SUBJECTS                    = "subjects";
    private static final String TABLE_TOPICS                      = "topics";
    private static final String TABLE_QUESTIONS                   = "questions";
    private static final String TABLE_STUDENTS                    = "students";
    private static final String TABLE_QUESTIONDATA                = "questionData";

    private static final String SUBJECTS_KEY_ID                   = "id";
    private static final String SUBJECTS_KEY_NAME                 = "subjectName";
    private static final String SUBJECTS_KEY_TOPICIDS             = "topicIDs";

    private static final String TOPICS_KEY_ID                     = "id";
    private static final String TOPICS_KEY_NAME                   = "topicName";
    private static final String TOPICS_KEY_QUESTIONIDS            = "questionIDs";

    private static final String QUESTIONS_KEY_ID                  = "id";
    private static final String QUESTIONS_KEY_QTEXT               = "qText";
    private static final String QUESTIONS_KEY_ANSWERCHOICEIDS     = "answerChoiceIDs";
    private static final String QUESTIONS_KEY_CORRECTANSWERCHOICE = "correctAnswerChoice";

    private static final String ANSWERCHOICES_KEY_ID              = "id";
    private static final String ANSWERCHOICES_KEY_ANSCHAR         = "ansChar";
    private static final String ANSWERCHOICES_KEY_ANSTEXT         = "ansText";

    private static final String STUDENTS_KEY_ID                   = "id";
    private static final String STUDENTS_KEY_USERNAME             = "username";
    private static final String STUDENTS_KEY_PASSWORD             = "password";
    private static final String STUDENTS_KEY_SUBJECTIDS           = "subjectIDs";
    private static final String STUDENTS_KEY_QUESTIONDATAIDS      = "questionDataIDs";

    public LocalDBHandler()
    {

    }

    public void updateFromDynamoDB()
    {
        DynamoHandler serverDB = new DynamoHandler();
        long serverVersion = serverDB.getVersion();
        if(serverVersion!=version)
        {

        }
        version = serverVersion;
    }

    public Subject getSubjectFromID(Long subjectID)
    {

    }

    public Subject setSubject(Subject subject)
    {

    }

    public void setSubjects(ArrayList<Subject> subject)
    {

    }

    public Topic getTopicFromID(Long questionID)
    {

    }

    public Topic setTopic(Topic topic)
    {
        return topic;
    }

    public Question getQuestionFromID(Long questionID)
    {

    }

    public AnswerChoice getAnswerChoiceFromID(Long answerChoiceID)
    {

    }

    public void setQuestionData(QuestionData q)
    {

    }

    public QuestionData getQuestionDataFromID(Long questionDataID)
    {

    }


}