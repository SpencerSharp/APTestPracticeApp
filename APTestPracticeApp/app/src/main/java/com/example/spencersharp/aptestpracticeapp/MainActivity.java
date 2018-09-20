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
    }

    public void loginregisterButtonPress(View v)
    {
        EditText usernameEditText = (EditText)findViewById(R.id.usernameEditText);
        String username = ""+usernameEditText.getText();
        TextView passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        String password = ""+passwordEditText.getText();
        toLoadingScreen(username,password);
    }

    public void toLoadingScreen(String username, String password)
    {
        Intent loadingAct = new Intent(this, LoadingScreenActivity.class);
        loadingAct.putExtra(MainActivity.activityNameKey,"MainActivity");
        loadingAct.putExtra(MainActivity.usernameKey,username);
        loadingAct.putExtra(MainActivity.passwordKey,password);
        startActivity(loadingAct);
    }
}
