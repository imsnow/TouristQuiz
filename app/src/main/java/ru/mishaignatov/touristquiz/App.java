package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.mishaignatov.touristquiz.orm.OrmDao;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String TAG = "Application";

    private static GameManager gameManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App onCreate");
        if(!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        // init game manager
        gameManager = GameManager.getInstance(this);
        // init db
        OrmDao.getInstance(this);

        gameManager.savePreference();
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}
