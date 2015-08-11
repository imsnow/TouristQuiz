package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ru.mishaignatov.touristquiz.data.Quiz;
import ru.mishaignatov.touristquiz.data.QuizStorage;

/**
 * Created by Ignatov on 05.08.2015.
 * Main application class
 * Initialize DB
 */
public class App extends Application {

    private static final String TAG = "Application";

    private static final String FILE = "quizzes.txt";
    private QuizStorage storage;

    private static SharedPreferences prefs;
    private static int total_size_file = 0;     // Сколько всего загадок находится в файле
    private static int answered_size = 0;       // Сколько пользователь отгадал загадок
    private static int score = 0;

    private static final String KEY_TOTAL    = "total";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_SCORE = "score";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App created");

        // Load previous result
        loadPreference();

        storage = QuizStorage.getStorage(this);

        // Check has the file changed?
        int current_total = storage.getNumberOfQuizzes(this, FILE);
        if(current_total != total_size_file) {
            // TODO update db - remove and create new from FILE
            storage.updateDB(this);
            storage.loadFile(this, FILE);
            total_size_file = current_total;
        }
        savePreference();
    }

    private void loadPreference(){
        prefs = getSharedPreferences("quiz", Context.MODE_PRIVATE);
        total_size_file = prefs.getInt(KEY_TOTAL,    0);
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

    public static int getTotalQuizzes()    {  return total_size_file; }
    public static int getAnsweredQuizzes() {  return answered_size;   }
    public static int getScore()           {  return score;           }
}
