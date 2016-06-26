package com.example.spencersharp.aptestpracticeapp;

import android.content.Context;

public class LocalDBPermission {
    private static Context context;

    public LocalDBPermission()
    {

    }

    public LocalDBPermission(Context context)
    {
        this.context = context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return context;
    }
}
