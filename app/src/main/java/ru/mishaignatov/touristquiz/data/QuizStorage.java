package ru.mishaignatov.touristquiz.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import ru.mishaignatov.touristquiz.database.DBHelper;

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
        createDB(context);
    }

    public static QuizStorage getStorage(Context context){
        if(storage == null) storage = new QuizStorage(context);
        return storage;
    }

    // Add Quiz to database
    private static void addQuiz(final Quiz quiz){

        ContentValues cv = new ContentValues();

        cv.put(DBHelper.QUIZ_TEXT, quiz.getText());
        cv.put(DBHelper.QUIZ_ANSWERS, quiz.getStringAnswers());
        cv.put(DBHelper.QUIZ_TYPE, quiz.getType());

        long rowId = db.insert(DBHelper.TABLE, null, cv);
        Log.d(TAG, "Added item, id = " + rowId);
    }

    // get random Quiz

    public static Quiz getQuiz(){
        Cursor cursor = db.query(DBHelper.TABLE, null, null, null, null, null, null);

        // определяем номера столбцов по имени в выборке
        int typeColIndex = cursor.getColumnIndex(DBHelper.QUIZ_TYPE);
        int textColIndex = cursor.getColumnIndex(DBHelper.QUIZ_TEXT);
        int answersColIndex = cursor.getColumnIndex(DBHelper.QUIZ_ANSWERS);

        int size = cursor.getCount();
        Random r = new Random();
        int random = r.nextInt(size);
        cursor.moveToPosition(random);
        Quiz quiz = new Quiz(cursor.getString(textColIndex), cursor.getString(answersColIndex), cursor.getString(typeColIndex));
        return quiz;
    }

    // Load file with quizzes
    public void loadFile(Context context, final String filename){

        AssetManager assets = context.getAssets();
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

    // Get numbers of quizzes in file
    public int getNumberOfQuizzes(Context context, final String filename){
        int n = 0;

        AssetManager assets = context.getAssets();
        try {
            InputStream is = assets.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while((reader.readLine()) != null) {
                n++;
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return n;
    }

    private void createDB(Context context){
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public void updateDB(Context context){
        if(db != null) {
            int n = db.delete(DBHelper.TABLE, null, null);
            Log.d(TAG, "Database deleted, entries = " + n);
        }
        createDB(context);
    }
}
