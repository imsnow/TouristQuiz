package ru.mishaignatov.touristquiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Random;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 * Class singletone is for store all quizzes
 */
public class QuizStorage {

    private static final String TAG = "Quiz Storage";

    private static QuizStorage storage = null;
    //private static Cursor listQuiz;
    private static SQLiteDatabase db;

    // Возможные типы загадок
    public static final int SIGHT   = 0;
    public static final int KITCHEN = 1;
    public static final int HISTORY = 2;
    public static final int GEO     = 3;

    // Initialize storage
    private QuizStorage(Context context) {

        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public QuizStorage getStorage(Context context){
        if(storage == null) storage = new QuizStorage(context);
        return storage;
    }

    // Add Quiz to database
    public static void addQuiz(final Quiz quiz){

        ContentValues cv = new ContentValues();

        cv.put(DBHelper.QUIZ_TEXT, quiz.getText());
        cv.put(DBHelper.QUIZ_ANSWERS, quiz.getText());
        cv.put(DBHelper.QUIZ_TYPE, quiz.getType());

        long rowId = db.insert(DBHelper.TABLE, null, cv);
        Log.d(TAG, "Added item, id = " + rowId);
    }

    // get random Quiz
    public static Quiz getQuiz(){
        Cursor cursor = db.query(DBHelper.TABLE, null, null, null, null, null, null);
        int random = new Random(cursor.getCount()).nextInt();
        cursor.moveToPosition(random);
        Quiz quiz = new Quiz(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
        return quiz;
    }
}
