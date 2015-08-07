package ru.mishaignatov.touristquiz.data;

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

    public static final String TABLE = "dbquiz";

    public static final String QUIZ_ID     = "_id";
    public static final String QUIZ_TEXT   = "quiz";
    public static final String QUIZ_ANSWERS = "answer";
    public static final String QUIZ_IS     = "is_answered";
    public static final String QUIZ_TYPE   = "type";

    public DBHelper(Context context) {
        super(context, "database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");

        db.execSQL("create table " + TABLE + "("
                + QUIZ_ID + " integer primary key autoincrement,"
                + QUIZ_TEXT + " text,"
                + QUIZ_ANSWERS + " text,"
                + QUIZ_TYPE + " text,"
                + QUIZ_IS + " integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade SQL Database: old version = " + oldVersion + " new version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
