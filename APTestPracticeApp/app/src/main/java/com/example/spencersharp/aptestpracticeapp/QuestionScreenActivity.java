package com.example.spencersharp.aptestpracticeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen);
        LocalDBHandler localDB = new LocalDBHandler();
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        Button selectAnswerAButton = (Button) findViewById(R.id.selectAnswerAButton);
        Button selectAnswerBButton = (Button) findViewById(R.id.selectAnswerBButton);
        Button selectAnswerCButton = (Button) findViewById(R.id.selectAnswerCButton);
        Button selectAnswerDButton = (Button) findViewById(R.id.selectAnswerDButton);
        Button selectAnswerEButton = (Button) findViewById(R.id.selectAnswerEButton);
        long questionID = getIntent().getLongExtra(TopicScreenActivity.questionIDKey, 1);
        Question localQuestion = localDB.getQuestionFromID(questionID);
        questionTextView.setText(localQuestion.getQText());
        ArrayList<Long> answerChoices = localQuestion.getAnswerChoiceIDsArrayList();
        for(Long answerChoiceID : answerChoices)
        {
            AnswerChoice answerChoice = localDB.getAnswerChoiceFromID(answerChoiceID);
            if(answerChoice.getAnsChar()==1)
            {
                selectAnswerAButton.setText(answerChoice.getAnsText());
            }
            else if(answerChoice.getAnsChar()==2)
            {
                selectAnswerBButton.setText(answerChoice.getAnsText());
            }
            else if(answerChoice.getAnsChar()==3)
            {
                selectAnswerCButton.setText(answerChoice.getAnsText());
            }
            else if(answerChoice.getAnsChar()==4)
            {
                selectAnswerDButton.setText(answerChoice.getAnsText());
            }
            else if(answerChoice.getAnsChar()==5)
            {
                selectAnswerEButton.setText(answerChoice.getAnsText());
            }
        }
    }
}
