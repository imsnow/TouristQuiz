package ru.mishaignatov.touristquiz.game;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.FacebookSdk;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String version = "0.8";
    private static String mVersion;

    private static final String TAG = "Application";
    private static Context mContext;

    public static final String[] BGs = {"img/bg_att.png", "img/bg_kit.png", "img/bg_geo.png", "img/bg_his.png"};
    public static final String[] CIRCLEs = {"img/circle_att.png", "img/circle_kit.png", "img/circle_geo.png", "img/circle_his.png"};

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App onCreate");
        //if(!BuildConfig.DEBUG)
        //Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
        FacebookSdk.sdkInitialize(mContext);

        mVersion = getString(R.string.version, version);
    }

    public static Context getContext(){
        return mContext;
    }
    public static String getVersion()  { return mVersion; }
}
