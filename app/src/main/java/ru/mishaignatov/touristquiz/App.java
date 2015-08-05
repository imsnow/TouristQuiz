package ru.mishaignatov.touristquiz;

import android.app.Application;
import android.util.Log;

import ru.mishaignatov.touristquiz.data.Quiz;
import ru.mishaignatov.touristquiz.data.QuizStorage;

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

        QuizStorage storage = QuizStorage.getStorage(this);

        // Test only
        Quiz quiz = new Quiz("В какой стране находятся Влеикие Пирамиды Гизы?", "Египет, Турция, Иран, Манголия", QuizStorage.SIGHT);
        storage.addQuiz(quiz);
        Quiz quiz1 = new Quiz("В какой стране находятся Коллезей?", "Италия, Испания, Греция, Румыния", QuizStorage.SIGHT);
        storage.addQuiz(quiz1);
    }
}
