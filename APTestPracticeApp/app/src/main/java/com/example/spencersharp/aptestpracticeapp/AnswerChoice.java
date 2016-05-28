package com.example.spencersharp.aptestpracticeapp;

/**
 * Created by spencersharp on 5/19/16.
 */
public class AnswerChoice
{
    public long _id;
    public String ansText;
    public char ansChar;

    public AnswerChoice()
    {

    }

    public AnswerChoice(String t, char c)
    {
        ansText = t;
        ansChar = c;
    }
}
