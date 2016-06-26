package com.example.spencersharp.aptestpracticeapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    private static long version = 6;

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

    private static final String STUDENTS_KEY_ID                      = "_id";
    private static final String STUDENTS_KEY_USERNAME                = "username";
    private static final String STUDENTS_KEY_PASSWORD                = "password";
    private static final String STUDENTS_KEY_SUBJECTIDS              = "subjectIDs";
    private static final String STUDENTS_KEY_QUESTIONDATAIDS         = "questionDataIDs";

    private static final String QUESTIONDATA_KEY_ID                  = "_id";
    private static final String QUESTIONDATA_KEY_QUESTIIONID         = "questionID";
    private static final String QUESTIONDATA_KEY_CORRECTANSWERCHOICE = "correctAnswerChoice";
    private static final String QUESTIONDATA_KEY_USERPREVCHOICE      = "userPrevChoice";

    DynamoHandler serverDB;

    public LocalDBHandler()
    {
        super(new LocalDBPermission().getContext(), DB_NAME, null, (int) version);
    }
    public LocalDBHandler(Context context)
    {
        super(context, DB_NAME, null, (int)version);
    }

    public LocalDBHandler(LocalDBPermission permission)
    {
        super(permission.getContext(),DB_NAME,null,(int) version);
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

        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + " (" + STUDENTS_KEY_ID
                + " INTEGER PRIMARY KEY," + STUDENTS_KEY_USERNAME + " TEXT," +
                STUDENTS_KEY_PASSWORD + " TEXT," + STUDENTS_KEY_SUBJECTIDS + " TEXT," +
                STUDENTS_KEY_QUESTIONDATAIDS + " TEXT" + ")";

        String CREATE_QUESTIONDATA_TABLE = "CREATE TABLE " + TABLE_QUESTIONDATA + "(" +
                QUESTIONDATA_KEY_ID + " INTEGER PRIMARY KEY," + QUESTIONDATA_KEY_QUESTIIONID + " TEXT,"
                + QUESTIONDATA_KEY_CORRECTANSWERCHOICE + " INTEGER," + QUESTIONDATA_KEY_USERPREVCHOICE + " INTEGER" + ")";

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

    public boolean updateNeeded()
    {
        serverDB = new DynamoHandler();
        long serverVersion = serverDB.getVersion();
        if(version!=serverVersion)
            return true;
        return false;
    }

    public void updateFromDynamoDB()
    {
        serverDB = new DynamoHandler();
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

    public void updateSubjects()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB subjects",""+serverDB.getSubjects());
        setSubjects(serverDB.getSubjects());
        Log.d("serverDB subjects", "" + getSubjects());
    }

    public void updateTopics()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB topics",""+serverDB.getTopics());
        setTopics(serverDB.getTopics());
        Log.d("localDB topics", "" + getTopics());
    }

    public void updateQuestions()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB questions",""+serverDB.getQuestions());
        setQuestions(serverDB.getQuestions());
        Log.d("localDB questions", "" + getQuestions());
    }

    public void updateAnswerChoices()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB answerChoices",""+serverDB.getAnswerChoices());
        setAnswerChoices(serverDB.getAnswerChoices());
        Log.d("localDB answerChoices", "" + getAnswerChoices());
    }

    public void updateStudents()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB students",""+serverDB.getStudents());
        setStudents(serverDB.getStudents());
        Log.d("localDB students", "" + getStudents());
    }

    public void updateQuestionData()
    {
        serverDB = new DynamoHandler();
        Log.d("serverDB students", "" + serverDB.getQuestionData());
        setQuestionData(serverDB.getQuestionData());
        Log.d("localDB questionData", "" + getQuestionData());
    }





    //Subject Methods
    public Subject getSubjectFromID(Long subjectID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_SUBJECTS, new String[]{SUBJECTS_KEY_ID,
                        SUBJECTS_KEY_NAME, SUBJECTS_KEY_TOPICIDS}, SUBJECTS_KEY_ID + "=?",
                new String[]{String.valueOf(subjectID)}, null, null, null, null);

        Subject subject = null;
        if (cursor!=null)
        {
            if(cursor.getCount()!=0)
            {
                cursor.moveToFirst();

                subject = new Subject(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
            }
        }

        return subject;
    }

    public Subject setSubject(Subject subject)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECTS_KEY_NAME, subject.getSubjectName());
        values.put(SUBJECTS_KEY_TOPICIDS, subject.getTopicIDsString());

        if(getSubjectFromID(subject.getID())==null)
        {
            newInd = db.insert(TABLE_SUBJECTS, null, values);
            //newInd = db.insert()
        }
        else
        {
            newInd = db.update(TABLE_SUBJECTS, values, SUBJECTS_KEY_ID + " = ?",
                    new String[] { String.valueOf(subject.getID()) });
        }
        db.close(); //Close the database
        subject.setID(newInd);
        return subject;
    }

    public Subject deleteSubject(Subject subject)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long subjectID = db.delete(TABLE_SUBJECTS, SUBJECTS_KEY_ID + " = ?",
                new String[]{String.valueOf(subject.getID())});
        db.close();
        return subject;
    }

    public ArrayList<Subject> getSubjects()
    {
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        /*cursor.moveToFirst();
        Log.d("yolo",cursor.getString(0));
        Log.d("yolo2",cursor.getString(1));*/
        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            if(cursor.getCount()!=0)
            {
                do {
                    Subject subject = new Subject();
                    subject.setID(Long.parseLong(cursor.getString(0)));
                    subject.setSubjectName(cursor.getString(1));
                    subject.setTopicIDsString(cursor.getString(2));
                    subjects.add(subject);
                } while (cursor.moveToNext());
            }
        }

        return subjects;
    }

    public ArrayList<Subject> setSubjects(ArrayList<Subject> subjects)
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
        return subjects;
    }





    //Topic Methods
    public Topic getTopicFromID(Long topicID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_TOPICS, new String[]{TOPICS_KEY_ID,
                        TOPICS_KEY_NAME, TOPICS_KEY_QUESTIONIDS}, TOPICS_KEY_ID + "=?",
                new String[]{String.valueOf(topicID)}, null, null, null, null);

        Topic topic = null;
        if (cursor!=null)
        {
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();

                topic = new Topic(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2));
            }
        }

        return topic;
    }

    public Topic setTopic(Topic topic)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOPICS_KEY_NAME, topic.getTopicName());
        values.put(TOPICS_KEY_QUESTIONIDS, topic.getQuestionIDsString());

        if(getTopicFromID(topic.getID())==null)
        {
            newInd = db.insert(TABLE_TOPICS, null, values);
        }
        else
        {
            newInd = db.update(TABLE_TOPICS, values, TOPICS_KEY_ID + " = ?",
                    new String[] { String.valueOf(topic.getID()) });
        }
        db.close(); //Close the database
        topic.setID(newInd);
        return topic;
    }

    public Topic deleteTopic(Topic topic)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int topicID = db.delete(TABLE_TOPICS, TOPICS_KEY_ID + " = ?",
                new String[]{String.valueOf(topic.getID())});
        db.close();
        return getTopicFromID((long)topicID);
    }

    public ArrayList<Topic> getTopics()
    {
        ArrayList<Topic> topics = new ArrayList<Topic>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TOPICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setID(Long.parseLong(cursor.getString(0)));
                topic.setTopicName(cursor.getString(1));
                topic.setQuestionIDsString(cursor.getString(2));
                topics.add(topic);
            } while (cursor.moveToNext());
        }

        return topics;
    }

    public ArrayList<Topic> setTopics(ArrayList<Topic> topics)
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
        return topics;
    }





    //Question Methods
    public Question getQuestionFromID(Long questionID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_QUESTIONS, new String[]{QUESTIONS_KEY_ID,
                        QUESTIONS_KEY_QTEXT, QUESTIONS_KEY_CORRECTANSWERCHOICE, QUESTIONS_KEY_CORRECTANSWERCHOICE}, QUESTIONS_KEY_ID + "=?",
                new String[]{String.valueOf(questionID)}, null, null, null, null);

        Question question = null;
        if(cursor!=null)
        {
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();
                question = new Question(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
            }
        }

        return question;
    }

    public Question setQuestion(Question question)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTIONS_KEY_QTEXT, question.getQText());
        values.put(QUESTIONS_KEY_ANSWERCHOICEIDS, question.getAnswerChoiceIDsString());
        values.put(QUESTIONDATA_KEY_CORRECTANSWERCHOICE, question.getCorrectAnswerChoice());

        if(getQuestionFromID(question.getID())==null)
        {
            newInd = db.insert(TABLE_QUESTIONS, null, values);
        }
        else
        {
            newInd = db.update(TABLE_QUESTIONS, values, QUESTIONS_KEY_ID + " = ?",
                    new String[] { String.valueOf(question.getID()) });
        }
        db.close(); //Close the database
        question.setID(newInd);
        return question;
    }

    public Question deleteQuestion(Question question)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long questionID = db.delete(TABLE_QUESTIONS, QUESTIONS_KEY_ID + " = ?",
                new String[]{String.valueOf(question.getID())});
        db.close();
        return getQuestionFromID(questionID);
    }

    public ArrayList<Question> getQuestions()
    {
        ArrayList<Question> questions = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setID(Long.parseLong(cursor.getString(0)));
                question.setQText(cursor.getString(1));
                question.setAnswerChoiceIDsString(cursor.getString(2));
                question.setCorrectAnswerChoice(Integer.parseInt(cursor.getString(3)));
                questions.add(question);
            } while (cursor.moveToNext());
        }

        return questions;
    }

    public ArrayList<Question> setQuestions(ArrayList<Question> questions)
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
        return questions;
    }





    //AnswerChoice Methods
    public AnswerChoice getAnswerChoiceFromID(Long answerChoiceID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_ANSWERCHOICES, new String[]{ANSWERCHOICES_KEY_ID,
                        ANSWERCHOICES_KEY_ANSCHAR, ANSWERCHOICES_KEY_ANSTEXT}, ANSWERCHOICES_KEY_ID + "=?",
                new String[]{String.valueOf(answerChoiceID)}, null, null, null, null);

        AnswerChoice answerChoice = null;
        if (cursor!=null) {
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();

                answerChoice = new AnswerChoice(Long.parseLong(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)), cursor.getString(2));
            }
        }

        return answerChoice;
    }

    public AnswerChoice setAnswerChoice(AnswerChoice answerChoice)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ANSWERCHOICES_KEY_ANSCHAR, answerChoice.getAnsChar());
        values.put(ANSWERCHOICES_KEY_ANSTEXT, answerChoice.getAnsText());


        if(getQuestionFromID(answerChoice.getID())==null)
        {
            newInd = db.insert(TABLE_ANSWERCHOICES, null, values);
        }
        else
        {
            newInd = db.update(TABLE_ANSWERCHOICES, values, ANSWERCHOICES_KEY_ID + " = ?",
                    new String[] { String.valueOf(answerChoice.getID()) });
        }
        db.close(); //Close the database
        answerChoice.setID(newInd);
        return answerChoice;
    }

    public AnswerChoice deleteAnswerChoice(AnswerChoice answerChoice)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long answerChoiceID = db.delete(TABLE_ANSWERCHOICES, ANSWERCHOICES_KEY_ID + " = ?",
                new String[]{String.valueOf(answerChoice.getID())});
        db.close();
        return getAnswerChoiceFromID(answerChoiceID);
    }

    public ArrayList<AnswerChoice> getAnswerChoices()
    {
        ArrayList<AnswerChoice> answerChoices = new ArrayList<AnswerChoice>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ANSWERCHOICES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AnswerChoice answerChoice = new AnswerChoice();
                answerChoice.setID(Long.parseLong(cursor.getString(0)));
                answerChoice.setAnsChar(Integer.parseInt(cursor.getString(1)));
                answerChoice.setAnsText(cursor.getString(2));
                answerChoices.add(answerChoice);
            } while (cursor.moveToNext());
        }

        return answerChoices;
    }

    public ArrayList<AnswerChoice> setAnswerChoices(ArrayList<AnswerChoice> answerChoices)
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
        return answerChoices;
    }





    //Student Methods
    public Student getStudentFromID(long studentID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{STUDENTS_KEY_ID,
                        STUDENTS_KEY_USERNAME, STUDENTS_KEY_PASSWORD, STUDENTS_KEY_SUBJECTIDS, STUDENTS_KEY_QUESTIONDATAIDS}, STUDENTS_KEY_ID + "=?",
                new String[]{String.valueOf(studentID)}, null, null, null, null);

        Student student = null;
        if(cursor!=null)
        {
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();
                Log.d("found",""+ studentID);
                student = new Student(Long.parseLong(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            }
        }
        return student;
    }

    public Student setStudent(Student student)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STUDENTS_KEY_USERNAME, student.getUsername());
        values.put(STUDENTS_KEY_PASSWORD, student.getPassword());
        values.put(STUDENTS_KEY_SUBJECTIDS, student.getSubjectIDsString());
        values.put(STUDENTS_KEY_QUESTIONDATAIDS, student.getQuestionDataIDsString());



        if(getStudentFromID(student.getID())==null)
        {
            Log.d("yay",""+student);
            newInd = db.insert(TABLE_STUDENTS, null, values);
        }
        else
        {
            newInd = db.update(TABLE_STUDENTS, values, STUDENTS_KEY_ID + " = ?",
                    new String[] { String.valueOf(student.getID()) });
        }
        db.close(); //Close the database
        student.setID(newInd);
        return student;
    }

    public Student deleteStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long studentID = db.delete(TABLE_STUDENTS, STUDENTS_KEY_ID + " = ?",
                new String[]{String.valueOf(student.getID())});
        db.close();
        return getStudentFromID(studentID);
    }

    public ArrayList<Student> getStudents()
    {
        ArrayList<Student> students = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setID(Long.parseLong(cursor.getString(0)));
                student.setUsername(cursor.getString(1));
                student.setPassword(cursor.getString(2));
                student.setSubjectIDsString(cursor.getString(3));
                student.setQuestionDataIDsString(cursor.getString(4));
                students.add(student);
            } while (cursor.moveToNext());
        }

        return students;
    }

    public ArrayList<Student> setStudents(ArrayList<Student> students)
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
        Log.d("studentsLocalDBHandler",""+getStudents());
        return students;
    }

    //QuestionData Methods
    public QuestionData getQuestionDataFromID(long questionDataID)
    {
        SQLiteDatabase db = this.getReadableDatabase(); //Returns this database to read from

        Cursor cursor = db.query(TABLE_QUESTIONDATA, new String[] { QUESTIONDATA_KEY_ID,
                        QUESTIONDATA_KEY_QUESTIIONID, QUESTIONDATA_KEY_CORRECTANSWERCHOICE, QUESTIONDATA_KEY_USERPREVCHOICE}, QUESTIONDATA_KEY_ID + "=?",
                new String[] { String.valueOf(questionDataID) }, null, null, null, null);

        QuestionData questionData = null;
        if(cursor!=null)
        {
            if(cursor.getCount()!=0) {
                cursor.moveToFirst();

                questionData = new QuestionData(Long.parseLong(cursor.getString(0)),
                        Long.parseLong(cursor.getString(1)), Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)));
            }
        }

        return questionData;
    }

    public QuestionData setQuestionData(QuestionData questionData)
    {
        long newInd = 0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(QUESTIONDATA_KEY_QUESTIIONID, questionData.getQuestionID());
        values.put(QUESTIONDATA_KEY_CORRECTANSWERCHOICE, questionData.getCorrectAnswerChoice());
        values.put(QUESTIONDATA_KEY_USERPREVCHOICE, questionData.getUserPrevChoice());

        if(getQuestionDataFromID(questionData.getID())==null)
        {
            newInd = db.insert(TABLE_QUESTIONDATA, null, values);
        }
        else
        {
            newInd = db.update(TABLE_QUESTIONDATA, values, QUESTIONS_KEY_ID + " = ?",
                    new String[] { String.valueOf(questionData.getID()) });
        }
        db.close(); //Close the database
        questionData.setID(newInd);
        return questionData;
    }

    public QuestionData deleteQuestionData(QuestionData questionData)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long questionDataID = db.delete(TABLE_QUESTIONDATA, QUESTIONDATA_KEY_ID + " = ?",
                new String[] { String.valueOf(questionData.getID()) });
        db.close();
        return getQuestionDataFromID(questionDataID);
    }

    public ArrayList<QuestionData> getQuestionData()
    {
        ArrayList<QuestionData> questionDatas = new ArrayList<QuestionData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONDATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QuestionData questionData = new QuestionData();
                questionData.setID(Long.parseLong(cursor.getString(0)));
                questionData.setQuestionID(Long.parseLong(cursor.getString(1)));
                questionData.setCorrectAnswerChoice(Integer.parseInt(cursor.getString(2)));
                questionData.setUserPrevChoice(Integer.parseInt(cursor.getString(3)));
                questionDatas.add(questionData);
            } while (cursor.moveToNext());
        }

        return questionDatas;
    }

    public ArrayList<QuestionData> setQuestionData(ArrayList<QuestionData> questionDataList)
    {
        //Standard AWS Accessing code
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
        return questionDataList;
    }
}