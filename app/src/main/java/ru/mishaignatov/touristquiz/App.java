package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.util.Log;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App created");
    }
}
