package ru.mishaignatov.touristquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ru.mishaignatov.touristquiz.App;
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

    private int fillTable(SQLiteDatabase db){

        // read Questions From FILE
        AssetManager assets = context.getAssets();
        InputStream is;
        int cnt=0;
        try {
            is = assets.open(FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null) {
                String[] arr = s.split(";");
                if (arr.length == 4) {
                    ContentValues cv = new ContentValues();
                    // Fill contentValues
                    cv.put(QuestionTable.COLUMN_QUIZ, arr[0].trim());
                    cv.put(QuestionTable.COLUMN_ANSWERS, arr[1].trim());
                    cv.put(QuestionTable.COLUMN_COUNTRY, arr[2].trim());
                    cv.put(QuestionTable.COLUMN_TYPE, arr[3].trim());
                    // insert this values
                    Log.d(TAG, "Added to database element, id = " + db.insert(QuestionTable.NAME, null, cv));
                    //db.insert(QuestionTable.NAME, null, cv);
                    // calculate all new questions
                    cnt++;
                }
            }
        is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Database was filled. Count = " + cnt);

        return cnt;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database " + QuestionTable.NAME);
        db.execSQL(QuestionTable.CREATE);

        App.setTotalQuizzes(fillTable(db));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade SQL Database: old version = " + oldVersion + " new version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.NAME);
        onCreate(db);
    }

    //=============================================================
    // Queries to DataBase
    //
    public ArrayList<String> getCountryList(SQLiteDatabase db){
        ArrayList<String> list = new ArrayList<>();
        Cursor c = db.query(QuestionTable.NAME, new String[]{QuestionTable.COLUMN_COUNTRY}, null, null, QuestionTable.COLUMN_COUNTRY,null,null);
        if(c!=null && c.moveToFirst()){
            do {
                // 0 - Country
                String s = c.getString(0);
                Log.d(TAG, "Query result: " + s);
                list.add(s);
            } while (c.moveToNext());
            c.close();
        }

        return list;
    }

    public ArrayList<Quiz> getQuizzesList(SQLiteDatabase db, String country){
        ArrayList<Quiz> list = new ArrayList<>();
        Cursor c = db.query(QuestionTable.NAME, QuestionTable.TAKE_QUIZZES, QuestionTable.COLUMN_COUNTRY + " = ?", new String[]{ country }, null, null, null);
        // 0 - Quiz, 1 - answers, 2 - type
        if(c != null && c.moveToFirst()){
            do {
                Quiz item = new Quiz(c.getString(0), c.getString(1), c.getString(2));
                list.add(item);
                Log.d(TAG, "Quiz item added. Text = " + item.getText() + " answers = " + item.getStringAnswers() + " type = " + item.getType());
            }while (c.moveToNext());
            c.close();
        }
        return list;
    }

}
