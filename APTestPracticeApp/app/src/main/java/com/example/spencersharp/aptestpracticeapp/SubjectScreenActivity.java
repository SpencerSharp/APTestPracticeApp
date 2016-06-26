package com.example.spencersharp.aptestpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class SubjectScreenActivity extends AppCompatActivity {

    static final String subjectIDKey = "com.example.spencersharp.subjectID";
    static Student student;
    ArrayList<Subject> subjectArrayList;
    long userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_screen);

        LocalDBHandler localDB = new LocalDBHandler();

        Intent thisIntent = getIntent();
        long studentID = thisIntent.getLongExtra(MainActivity.userIDKey,0);
        student = localDB.getStudentFromID(studentID);

        Log.d("login2", "" + studentID);
        Log.d("student",""+student);
        final ListView subjectList = (ListView) findViewById(R.id.mainSubjectList);
        subjectArrayList = student.getSubjects();
        Log.d("subjectsArrayList",""+subjectArrayList);
        ArrayList<String> subjectNames = new ArrayList<>();
        for(Subject s : subjectArrayList)
            subjectNames.add(s.getSubjectName());
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subjectNames);
        subjectList.setAdapter(categoryArrayAdapter);
        subjectList.setClickable(true);
        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) { //Whenever a different item on the menu is selected

                Object o = subjectList.getItemAtPosition(position);
                long sID = subjectArrayList.get(position).getID();
                toTopicScreen(sID);
            }
        });
    }

    public void logOutButtonPressed(View v)
    {

    }

    public void addSubjectButtonPressed(View v)
    {

    }

    public void leaderboardButtonPressed(View v)
    {

    }

    public void toTopicScreen(long subjectID)
    {
        Intent topicScreenIntent = new Intent(this,TopicScreenActivity.class);
        topicScreenIntent.putExtra(subjectIDKey,subjectID);
        startActivity(topicScreenIntent);
    }
}
