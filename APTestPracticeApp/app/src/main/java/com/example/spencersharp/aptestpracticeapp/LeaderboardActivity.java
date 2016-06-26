package com.example.spencersharp.aptestpracticeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    long subjectID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_screen);

        LocalDBHandler localDB = new LocalDBHandler();
        Intent thisIntent = getIntent();
        long subjectID = thisIntent.getLongExtra(SubjectScreenActivity.subjectIDKey, 0);
        String subjectName = localDB.getSubjectFromID(subjectID).getSubjectName();


        TextView leaderboardScreenTitle = (TextView) findViewById(R.id.leaderboardScreenTitle);
        leaderboardScreenTitle.setText(subjectName + " Leaderboard");

        ArrayList<String> gridInfo = new ArrayList<String>();
        ArrayList<Student> students = localDB.getStudents();
        ArrayList<Student> rankedStudents = new ArrayList<>();
        ArrayList<Integer> spotsOfStudents = new ArrayList<>();
        int loops = students.size();
        for(int i = 0; i < loops; i++)
        {
            int index = 0;
            for(int j = 0; j < students.size(); j++)
            {
                if(!spotsOfStudents.contains(j))
                {
                    index = j;
                    break;
                }
            }
            Student bestStudent = students.get(index);
            int maxScore = bestStudent.getTotalQuestionsRightFromSubject(subjectID);
            for(int j = 0; j < loops; j++)
            {
                Student curStudent = students.get(j);
                int curScore = curStudent.getTotalQuestionsRightFromSubject(subjectID);
                if(curScore>maxScore)
                {
                    index = j;
                    maxScore = curScore;
                    bestStudent = students.get(index);
                }
            }
            rankedStudents.add(bestStudent);
            spotsOfStudents.add(index);
        }
        for(int i = 0; i < rankedStudents.size(); i++)
        {
            gridInfo.add(""+(i+1));
            gridInfo.add(rankedStudents.get(i).getUsername());
            gridInfo.add(""+rankedStudents.get(i).getTotalQuestionsRightFromSubject(subjectID));
        }
    }

    public void backButtonPressed()
    {
        Intent subjectScreenActivityIntent = new Intent(this, SubjectScreenActivity.class);
        subjectScreenActivityIntent.putExtra(MainActivity.userIDKey,subjectID);
        startActivity(subjectScreenActivityIntent);
    }
}
