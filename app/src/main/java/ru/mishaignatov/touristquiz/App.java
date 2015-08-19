package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.mishaignatov.touristquiz.data.AssetsLoader;
import ru.mishaignatov.touristquiz.data.Country;
import ru.mishaignatov.touristquiz.data.CountryStorage;
import ru.mishaignatov.touristquiz.data.QuizStorage;
import ru.mishaignatov.touristquiz.database.DBHelper;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String TAG = "Application";

    private static final String FILE = "quizzes.txt";
    private List<String> list = new ArrayList<>();
    private QuizStorage storage;

    private static SharedPreferences prefs;
    private static int total_size_file = 0;     // Сколько всего загадок находится в файле
    private static int answered_size = 0;       // Сколько пользователь отгадал загадок
    private static int score = 0;

    private static final String KEY_TOTAL    = "total";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_SCORE = "score";

    private DBHelper mDbHelper;
    private SQLiteDatabase mDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App created");

        // Load previous result
        loadPreference();

        mDbHelper = DBHelper.getInstance(this);
        mDataBase = mDbHelper.getWritableDatabase();

        list = mDbHelper.getCountryColumn(mDataBase);
        /*
        AssetsLoader loader = AssetsLoader.getLoader(this, CountryStorage.getStorage());

        storage = QuizStorage.getStorage(this);

        // Check has the file changed?
        int current_total = storage.getNumberOfQuizzes(this, FILE);
        if(current_total != total_size_file) {
            // TODO update db - remove and create new from FILE
            storage.updateDB(this);
            storage.loadFile(this, FILE);
            total_size_file = current_total;
        }
        */
        savePreference();
    }

    private void loadPreference(){
        prefs = getSharedPreferences("quiz", Context.MODE_PRIVATE);
        total_size_file = prefs.getInt(KEY_TOTAL, 0);
        answered_size   = prefs.getInt(KEY_ANSWERED, 0);
        score           = prefs.getInt(KEY_SCORE,    0);
    }

    private static void savePreference(){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(KEY_TOTAL, total_size_file);
        edit.putInt(KEY_ANSWERED, answered_size);
        edit.putInt(KEY_SCORE, score);
        edit.apply();

        Log.d(TAG, "Preference saved, total = " + total_size_file + " answered = " + answered_size + " score = " + score);
    }

    public static void userAnsweredTrue(){
        answered_size++;
        score+=30;
        savePreference();
    }

    public static void setTotalQuizzes(int n)   { total_size_file = n;}
    public static int getTotalQuizzes()    {  return total_size_file; }
    public static int getAnsweredQuizzes() {  return answered_size;   }
    public static int getScore()           {  return score;           }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDbHelper.close();
    }

    @Override
    public void onTerminate(){
        Log.d(TAG, "App terminate");
        mDbHelper.close();
        super.onTerminate();
    }
}
