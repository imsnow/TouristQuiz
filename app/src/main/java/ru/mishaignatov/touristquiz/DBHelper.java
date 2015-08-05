package ru.mishaignatov.touristquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 * SQLite data base helper
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = "DBHelper";

    private static final String TABLE = "dbquiz";

    private static final String QUIZ_TEXT   = "quiz";
    private static final String QUIZ_ANSWER = "answer";
    private static final String QUIZ_IS     = "is_answered";
    private static final String QUIZ_TYPE   = "type";

    public DBHelper(Context context) {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");

        db.execSQL("create table " + TABLE + "("
                + "id integer primary key autoincrement"
                + "quiz text"
                + "answer text"
                + "type text"
                + "is_answered integer" + ");");
    }

    public static void addQuiz(SQLiteDatabase db, final Quiz quiz){
        ContentValues cv = new ContentValues();

        cv.put(QUIZ_TEXT, quiz.getText());
        cv.put(QUIZ_TYPE, quiz.getType());

        db.insert(TABLE, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");
    }
}
