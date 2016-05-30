package com.example.spencersharp.aptestpracticeapp;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class LocalDBHandler extends SQLiteOpenHelper
{
    /*Ideas:
    LocalDB is versioned, copies from the DynamoDB on launch if version isnt the same.
    LocalDB is for everything except users
    All reading of questions, subjects, server info basically, is done via LocalDB.

    For users, a local user variable is read from the DB on login, and copied into the app.
    In the app, each time the local user variable is modified, a web connection check is performed.
    If you're connected, the local user will have its version incremented and will be written to DynamoDB
    */
    private static long version;

    private static final String DB_NAME                              = "APTestPracticeLocalDB";

    private static final String TABLE_SUBJECTS                       = "subjects";
    private static final String TABLE_TOPICS                         = "topics";
    private static final String TABLE_QUESTIONS                      = "questions";
    private static final String TABLE_ANSWERCHOICES                  = "answerChoices";
    private static final String TABLE_STUDENTS                       = "students";
    private static final String TABLE_QUESTIONDATA                   = "questionData";

    private static final String SUBJECTS_KEY_ID                      = "_id";
    private static final String SUBJECTS_KEY_NAME                    = "subjectName";
    private static final String SUBJECTS_KEY_TOPICIDS                = "topicIDs";

    private static final String TOPICS_KEY_ID                        = "_id";
    private static final String TOPICS_KEY_NAME                      = "topicName";
    private static final String TOPICS_KEY_QUESTIONIDS               = "questionIDs";

    private static final String QUESTIONS_KEY_ID                     = "_id";
    private static final String QUESTIONS_KEY_QTEXT                  = "qText";
    private static final String QUESTIONS_KEY_ANSWERCHOICEIDS        = "answerChoiceIDs";
    private static final String QUESTIONS_KEY_CORRECTANSWERCHOICE    = "correctAnswerChoice";

    private static final String ANSWERCHOICES_KEY_ID                 = "_id";
    private static final String ANSWERCHOICES_KEY_ANSCHAR            = "ansChar";
    private static final String ANSWERCHOICES_KEY_ANSTEXT            = "ansText";

    private static final String STUDENTS_KEY_ID                      = "id";
    private static final String STUDENTS_KEY_USERNAME                = "username";
    private static final String STUDENTS_KEY_PASSWORD                = "password";
    private static final String STUDENTS_KEY_SUBJECTIDS              = "subjectIDs";
    private static final String STUDENTS_KEY_QUESTIONDATAIDS         = "questionDataIDs";

    private static final String QUESTIONDATA_KEY_ID                  = "_id";
    private static final String QUESTIONDATA_KEY_CORRECTANSWERCHOICE = "correctAnswerChoice";
    private static final String QUESTIONDATA_KEY_USERPREVCHOICE      = "userPrevChoice";

    public LocalDBHandler(Context context)
    {
        super(context, DB_NAME, null, (int)version);
    }

    public void onCreate(SQLiteDatabase localDB)
    {
        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "(" + SUBJECTS_KEY_ID +
                " INTEGER PRIMARY KEY," + SUBJECTS_KEY_NAME + " TEXT," + SUBJECTS_KEY_TOPICIDS +
                " TEXT" + ")";

        String CREATE_TOPICS_TABLE = "CREATE TABLE " + TABLE_TOPICS + "(" + TOPICS_KEY_ID +
                " INTEGER PRIMARY KEY," + TOPICS_KEY_NAME + " TEXT," + TOPICS_KEY_QUESTIONIDS +
                " TEXT" + ")";

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "(" + QUESTIONS_KEY_ID
                + " INTEGER PRIMARY KEY," + QUESTIONS_KEY_QTEXT + " TEXT," + QUESTIONS_KEY_ANSWERCHOICEIDS
                + " TEXT, " + QUESTIONS_KEY_CORRECTANSWERCHOICE + " INTEGER" + ")";

        String CREATE_ANSWERCHOICES_TABLE = "CREATE TABLE " + TABLE_ANSWERCHOICES + "(" +
                ANSWERCHOICES_KEY_ID + " INTEGER PRIMARY KEY," + ANSWERCHOICES_KEY_ANSCHAR +
                " INTEGER," + ANSWERCHOICES_KEY_ANSTEXT + " TEXT" + ")";

        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "(" + STUDENTS_KEY_ID
                + " INTEGER PRIMARY KEY," + STUDENTS_KEY_USERNAME + " TEXT," +
                STUDENTS_KEY_PASSWORD + " TEXT," + STUDENTS_KEY_SUBJECTIDS + " TEXT," +
                STUDENTS_KEY_QUESTIONDATAIDS + " TEXT" + ")";

        String CREATE_QUESTIONDATA_TABLE = "CREATE TABLE " + TABLE_QUESTIONDATA + "(" +
                QUESTIONDATA_KEY_ID + " INTEGER PRIMARY KEY," + QUESTIONDATA_KEY_CORRECTANSWERCHOICE
                + " INTEGER," + QUESTIONDATA_KEY_USERPREVCHOICE + " INTEGER" + ")";

        localDB.execSQL(CREATE_SUBJECTS_TABLE);
        localDB.execSQL(CREATE_TOPICS_TABLE);
        localDB.execSQL(CREATE_QUESTIONS_TABLE);
        localDB.execSQL(CREATE_ANSWERCHOICES_TABLE);
        localDB.execSQL(CREATE_STUDENTS_TABLE);
        localDB.execSQL(CREATE_QUESTIONDATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERCHOICES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONDATA);

        onCreate(db);
    }

    /*
    public void updateFromDynamoDB()
    {
        DynamoHandler serverDB = new DynamoHandler();
        long serverVersion = serverDB.getVersion();
        if(serverVersion!=version)
        {
            setSubjects(serverDB.getSubjects());
            setTopics(serverDB.getTopics());
            setQuestions(serverDB.getQuestions());
            setAnswerChoices(serverDB.getAnswerChoices());
            setStudents(serverDB.getStudents());
            setQuestionData(serverDB.getQuestionData());
        }
        version = serverVersion;
    }

    //Subject Methods
    public Subject getSubjectFromID(Long subjectID)
    {

    }

    public void setSubject(Subject subject)
    {

    }

    public void deleteSubject(Subject subject)
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

    public void setQuestionData(QuestionData questionData)
    {

    }

    public void setQuestionData(ArrayList<QuestionData> questionData)
    {

    }

    public QuestionData getQuestionDataFromID(Long questionDataID)
    {

    }
    */
}