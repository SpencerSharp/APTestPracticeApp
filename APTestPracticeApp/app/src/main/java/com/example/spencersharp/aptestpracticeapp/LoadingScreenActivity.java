package com.example.spencersharp.aptestpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class LoadingScreenActivity extends AppCompatActivity {

    public void displayText(String text)
    {
        TextView loadingLabel = (TextView) findViewById(R.id.loadingLabel);
        loadingLabel.setText(text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        //Check if username and password are valid

        Intent thisIntent = getIntent();
        String screenFrom = thisIntent.getStringExtra(MainActivity.activityNameKey);

        DynamoHandler serverDB = new DynamoHandler();
        LocalDBHandler localDB = new LocalDBHandler();

        if(screenFrom.equals("MainActivity"))
        {
            String username = thisIntent.getStringExtra(MainActivity.usernameKey);
            String password = thisIntent.getStringExtra(MainActivity.passwordKey);
            //If account already created, valid login info
            displayText("Attempting Login");
            long studentID = serverDB.tryLogin(username, password);

            if (studentID != -1)
            {
                displayText("Logging in...");
                //Flash "Logging in..."
                //Check Version
                if (localDB.updateNeeded())
                {
                    displayText("Updating subjects...");
                    localDB.updateSubjects();
                    displayText("Updating topics...");
                    localDB.updateTopics();
                    displayText("Updating questions...");
                    localDB.updateQuestions();
                    displayText("Updating answer choices");
                    localDB.updateAnswerChoices();
                    displayText("Updating User Data...");
                    localDB.updateStudents();
                    localDB.updateQuestionData();
                    displayText("Logging in...");
                }
            }
            else
            {
                studentID = serverDB.tryRegister(username, password);
                if (studentID == -1) //Failed to register AKA incorrect password
                {
                    //If username is taken (AKA incorrect password)
                    displayText("Incorrect password, returning to login screen...");
                    toMainActivity();
                }
                else //If account not already created, but username not taken
                {
                    //boolean doesUserWantToRegister = askUserAboutRegistering(); //Pop up button "Would you like to register new user $username?"
                    //if (doesUserWantToRegister) //If yes button pressed
                    //{
                        displayText("Registering account...");
                        localDB.updateSubjects(); //Download Subjects
                        displayText("Downloading subjects..."); //Flash "Downloading Subjects"
                        localDB.updateTopics(); //Download Topics
                        displayText("Downloading topics..."); //Flash "Downloading Topics"
                        localDB.updateQuestions(); //Download Questions
                        displayText("Downloading questions..."); //Flash "Downloading Questions"
                        localDB.updateAnswerChoices(); //Download AnswerChoices
                        displayText("Downloading answer choices...");
                        localDB.updateQuestionData();
                        displayText("Downloading User Data...");
                        localDB.updateStudents();
                        displayText("Logging in..."); //Flash "Logging in..."
                    //}
                    //else
                    //{
                    //    displayText("Returning to login screen...");
                    //    serverDB.deleteStudent(serverDB.getStudentFromID(studentID));
                    //    toMainActivity();
                    //}
                }
            }
            toSubjectScreen(studentID);
        }
        else if(screenFrom.equals("QuestionScreenActivity"))
        {

        }
    }

    public boolean askUserAboutRegistering()
    {
        return true;
    }

    public void toMainActivity()
    {
        Intent mainActivityIntent = new Intent(this,MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void toSubjectScreen(long id)
    {
        Intent subjectScreenActivityIntent = new Intent(this,SubjectScreenActivity.class);
        LocalDBHandler localDB = new LocalDBHandler();
        Log.d("students",""+localDB.getStudents());
        Log.d("login", "" + id);
        subjectScreenActivityIntent.putExtra(MainActivity.userIDKey,id);
        startActivity(subjectScreenActivityIntent);
    }
}