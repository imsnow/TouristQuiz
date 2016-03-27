package ru.mishaignatov.touristquiz.game;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;

import io.fabric.sdk.android.Fabric;
import ru.mishaignatov.touristquiz.R;
import ru.mishaignatov.touristquiz.database.DbHelper;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    public static final String version = ru.mishaignatov.touristquiz.BuildConfig.VERSION_NAME;
    private static String mVersion;

    private static final String TAG = "Application";
    private static Context mContext;

    public static final String[] BGs = {"img/bg_att.png", "img/bg_kit.png", "img/bg_geo.png", "img/bg_his.png"};
    public static final String[] CIRCLEs = {"img/circle_att.png", "img/circle_kit.png", "img/circle_geo.png", "img/circle_his.png"};

    private static DbHelper mDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App onCreate");
        //if(!BuildConfig.DEBUG)

        Fabric.with(this, new Crashlytics());

        mContext = getApplicationContext();
        FacebookSdk.sdkInitialize(mContext);

        mVersion = getString(R.string.version, version);

        mDbHelper = new DbHelper(this);
    }

    public static Context getContext(){
        return mContext;
    }
    public static String getVersion()  { return mVersion; }
    public static DbHelper getDbHelper(){ return mDbHelper; }
}
