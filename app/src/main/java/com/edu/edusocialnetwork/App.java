package com.edu.edusocialnetwork;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("ebn8XasSQJiIts1qTx4RWxCumCUpVNpw9dtqf4eM")
                // if defined
                .clientKey("IpiSs0jHuIE7KnGmPZTSZX51AD7aofaepWkF9tZ6")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
