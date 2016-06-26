package com.example.spencersharp.aptestpracticeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String activityNameKey = "com.example.spencersharp.activityNameKey";
    static final String usernameKey = "com.example.spencersharp.usernameKey";
    static final String passwordKey = "com.example.spencersharp.passwordKey";
    static final String userIDKey = "com.example.spencersharp.userIDKey";



    public int curClassSelected;
    Context c;
    public static int b;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Check localDB vs. dynamoDB version

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalDBPermission permission = new LocalDBPermission(this);
        DynamoCredentials credentials = new DynamoCredentials(getResources().getString(R.string.db_accessKey),getResources().getString(R.string.db_secretKey));
        LocalDBHandler localDBHandler = new LocalDBHandler();

        /*final ListView classListView = (ListView) findViewById(R.id.mainClassesList);
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
        });*/
    }

    public void loginregisterButtonPress(View v)
    {
        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        String username = ""+usernameEditText.getText();
        TextView passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        String password = ""+passwordEditText.getText();
        toLoadingScreen(username,password);
    }

    /*public void displayUsernameTakenError()
    {

    }*/

    public void toLoadingScreen(String username, String password)
    {
        Intent loadingAct = new Intent(this, LoadingScreenActivity.class);
        loadingAct.putExtra(MainActivity.activityNameKey,"MainActivity");
        loadingAct.putExtra(MainActivity.usernameKey,username);
        loadingAct.putExtra(MainActivity.passwordKey,password);
        startActivity(loadingAct);
    }
}
