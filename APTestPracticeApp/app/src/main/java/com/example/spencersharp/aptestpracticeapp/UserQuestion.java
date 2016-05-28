package com.example.spencersharp.aptestpracticeapp;

/**
 * Created by spencersharp on 5/19/16.
 */
public class UserQuestion extends Question
{
    public char userPrevChoice;

    public UserQuestion()
    {

    }

    public UserQuestion(Question q)
    {
        id = q.getID();
    }

    public boolean isQuestionAttempted()
    {
        if (userPrevChoice != 0)
            return true;
        return false;
    }

    public boolean isQuestionCorrect()
    {
        if(userPrevChoice==correctAnswerChoice)
            return true;
        return false;
    }

    public char getUserPrevChoice()
    {
        return userPrevChoice;
    }
}
