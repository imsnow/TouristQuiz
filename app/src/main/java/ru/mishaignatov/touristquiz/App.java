package ru.mishaignatov.touristquiz;

import android.app.Application;
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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "App created");

        storage = QuizStorage.getStorage(this);

        readFile(FILE);
        /*
        // Test only
        Quiz quiz = new Quiz("В какой стране находятся Влеикие Пирамиды Гизы?", "Египет, Турция, Иран, Манголия", QuizStorage.SIGHT);
        storage.addQuiz(quiz);
        Quiz quiz1 = new Quiz("В какой стране находятся Коллезей?", "Италия, Испания, Греция, Румыния", QuizStorage.SIGHT);
        storage.addQuiz(quiz1);
        */
    }


    private void readFile(final String filename){

        AssetManager assets = getAssets();
        try {
            InputStream is = assets.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null) {

                String[] arr = s.split(";");
                if (arr.length == 3) {
                    Quiz quiz = new Quiz(arr);
                    storage.addQuiz(quiz);
                }
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
