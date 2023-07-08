package com.example.appproject.Activities;

import android.app.Application;

import com.example.appproject.Utillities.SignalGenerator;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SignalGenerator.init(this);
    }
}
