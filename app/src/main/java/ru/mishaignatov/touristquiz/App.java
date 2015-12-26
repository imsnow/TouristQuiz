package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

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
        Log.d(TAG, "App onCreate");
        if(!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        User user = User.getUser(this);
        Log.d(TAG, user.toString());
    }
}
