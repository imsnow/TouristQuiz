package ru.mishaignatov.touristquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ru.mishaignatov.touristquiz.data.Quiz;

/**
 * Created by Ignatov Misha on 02.08.15.
 *
 * SQLite data base helper
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String TAG = "DBHelper";
    private static DBHelper helper;
    public static final String TABLE = "database";

    private static final String FILE = "questions.txt";

    public static final String QUIZ_ID     = "_id";
    public static final String QUIZ_TEXT   = "quiz";
    public static final String QUIZ_ANSWERS = "answer";
    public static final String QUIZ_IS     = "is_answered";
    public static final String QUIZ_TYPE   = "type";

    private Context context;

    private DBHelper(Context context) {
        super(context, TABLE, null, 1);
        this.context = context;
    }

    public static DBHelper getInstance(Context context){
        if(helper == null) helper = new DBHelper(context);
        return helper;
    }

    private int fillTable(SQLiteDatabase db) throws IOException {

        // read Questions From FILE
        AssetManager assets = context.getAssets();
        InputStream is = assets.open(FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        int cnt=0;
        String s;
        while((s = reader.readLine()) != null) {
            cnt++;
            String[] arr = s.split(";");
            if (arr.length == 4) {
                ContentValues cv = new ContentValues();
                // Fill contentValues
                cv.put(QuestionTable.COLUMN_QUIZ, arr[0]);
                cv.put(QuestionTable.COLUMN_ANSWERS, arr[1]);
                cv.put(QuestionTable.COLUMN_COUNTRY, arr[2]);
                cv.put(QuestionTable.COLUMN_TYPE, arr[3]);
                // insert this values
                db.insert(QuestionTable.NAME, null, cv);
            }
        }
        is.close();
        return cnt;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        db.execSQL(QuestionTable.CREATE);

        try {
            int n = fillTable(db);
            Log.d(TAG, "Fill database " + n + " question(s)");
        } catch (IOException e) {
            Log.d(TAG, "Error with fille table");
            e.printStackTrace();
        }

        /*
        db.execSQL("create table " + TABLE + "("
                + QUIZ_ID + " integer primary key autoincrement,"
                + QUIZ_TEXT + " text,"
                + QUIZ_ANSWERS + " text,"
                + QUIZ_TYPE + " text,"
                + QUIZ_IS + " integer" + ");");
                */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade SQL Database: old version = " + oldVersion + " new version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.NAME);
        onCreate(db);
    }
}
