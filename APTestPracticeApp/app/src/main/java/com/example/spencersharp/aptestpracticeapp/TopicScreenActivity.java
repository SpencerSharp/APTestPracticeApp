package com.example.spencersharp.aptestpracticeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TopicScreenActivity extends AppCompatActivity {

    static final String questionIDKey = "com.example.spencersharp.questionIDKey";

    long subjectID;
    ArrayList<Topic> topics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topic_screen);

        Intent thisIntent = getIntent();
        subjectID = thisIntent.getLongExtra(SubjectScreenActivity.subjectIDKey,0);
        LocalDBHandler localDB = new LocalDBHandler();
        setTitleText(localDB.getSubjectFromID(subjectID).getSubjectName());

        final ListView mainTopicList = (ListView) findViewById(R.id.mainTopicList);
        ArrayList<String> topicNames = new ArrayList<String>();
        //Making Topic list and stuff
        ArrayList<Topic> topicsInit = localDB.getSubjectFromID(subjectID).getTopicArrayList();
        topics = new ArrayList<Topic>();
        int loops = topicsInit.size();
        for(int i = 0; i < loops; i++)
        {
            Topic minTopic = topicsInit.get(1);
            String minString = minTopic.getTopicName();

            for(int j = 0; j < topicsInit.size(); j++)
            {
                String curString = topicsInit.get(j).getTopicName();
                if(minString.compareTo(curString)<0 && !topicNames.contains(curString))
                {
                    minTopic = topicsInit.get(j);
                    minString = curString;
                }

            }
            topics.add(minTopic);
            topicNames.add(minString);
        }
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,topicNames);
        mainTopicList.setAdapter(categoryArrayAdapter);
        mainTopicList.setClickable(true);
        mainTopicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) { //Whenever a different item on the menu is selected

                Object o = mainTopicList.getItemAtPosition(position);
                Topic topicClicked = topics.get(position);
                goToQuestionScreen(topicClicked.getID());
            }
        });
    }

    public void leaderboardButtonPressed(View v)
    {
        goToLeaderboard(subjectID);
    }

    public void toMainMenuButtonPressed(View v)
    {

    }

    public void setTitleText(String s)
    {
        TextView topicScreenTitle = (TextView) findViewById(R.id.topicScreenTitle);
        topicScreenTitle.setText(s);
    }

    public void goToLeaderboard(long subID)
    {
        Intent questionScreenActivityIntent = new Intent(this,LeaderboardActivity.class);
        questionScreenActivityIntent.putExtra(SubjectScreenActivity.subjectIDKey,subID);
        startActivity(questionScreenActivityIntent);
    }

    public void goToQuestionScreen(long topicID)
    {
        ArrayList<Question> posQuestions = SubjectScreenActivity.student.getQuestionsInTopicNotAttempted(topicID);
        int rand = (int)(Math.random() * ((posQuestions.size()-1) + 1));
        long qID = posQuestions.get(rand).getID();

        Intent questionScreenActivityIntent = new Intent(this,QuestionScreenActivity.class);
        questionScreenActivityIntent.putExtra(questionIDKey,qID);
        startActivity(questionScreenActivityIntent);
    }
}
