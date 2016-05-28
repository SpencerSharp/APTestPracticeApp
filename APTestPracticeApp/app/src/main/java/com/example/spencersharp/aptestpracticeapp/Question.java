package com.example.spencersharp.aptestpracticeapp;

import java.util.ArrayList;


public class Question
{
    public long id;
    public String qText;
    public ArrayList<AnswerChoice> answerChoices;
    public char correctAnswerChoice;


    public Question()
    {

    }


    public Question(long id, String qText, ArrayList<AnswerChoice> answerChoices, char correctAnswerChoice)
    {

    }


    public long getID()
    {
        return id;
    }


    public String getQText()
    {
        return qText;
    }


    public ArrayList<AnswerChoice> getAnswerChoices()
    {
        return answerChoices;
    }


    public char getCorrectAnswerChoice()
    {
        return correctAnswerChoice;
    }
}