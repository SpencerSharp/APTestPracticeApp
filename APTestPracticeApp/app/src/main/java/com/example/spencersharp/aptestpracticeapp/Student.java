package com.example.spencersharp.aptestpracticeapp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by spencersharp on 5/2/16.
 */
public class Student
{
    public long _id;
    public String subjectIDsString;

    public Student()
    {

    }

    public Student(long id, String subjectIDsString, )

    public ArrayList<Long> getSubjectIDsArrayList()
    {
        String[] subjectIDsArray = subjectIDsString.split("-");
        ArrayList<Long> subjectIDsArrayList = (ArrayList)Arrays.asList(subjectIDsArray);
        return subjectIDsArrayList;
    }

    public ArrayList<Subject> getSubjects()
    {
        ArrayList<Long> subjectIDsArrayList = getSubjectIDsArrayList();
        ArrayList<Subject> subjects = new ArrayList<Subject>();
        return subjects;
    }
}
