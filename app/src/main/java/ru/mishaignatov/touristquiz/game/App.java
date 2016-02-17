package ru.mishaignatov.touristquiz.game;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String TAG = "Application";
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App onCreate");
        //if(!BuildConfig.DEBUG)
        //Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
        FacebookSdk.sdkInitialize(mContext);
        //User user = User.getUser(this);
        //Log.d(TAG, user.toString());
    }

    public static Context getContext(){
        return mContext;
    }
}
