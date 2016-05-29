package com.example.spencersharp.aptestpracticeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public int curClassSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Check localDB vs. dynamoDB version
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView classListView = (ListView) findViewById(R.id.mainClassesList);
        final ArrayList<String> classList = new ArrayList<String>();
        classList.add("Biology");
        classList.add("Physics C");
        classList.add("English Language");

        //Code for accessing database
        Intent inten = getIntent();
        long userID = 0;



        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,classList);
        classListView.setAdapter(categoryArrayAdapter);
        classListView.setClickable(true);
        classListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //This creates the listener for the menu
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) { //Whenever a different item on the menu is selected
                Adapter ad = arg0.getAdapter();

                Object o = classListView.getItemAtPosition(position);
                System.out.println(o);
                curClassSelected = position; //Set the curCatSelected to curCatSelected
                arg1.setSelected(true);
            }
        });
    }
}
