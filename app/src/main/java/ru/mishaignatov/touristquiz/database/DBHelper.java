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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.mishaignatov.touristquiz.App;
import ru.mishaignatov.touristquiz.data.Country;
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
                    cv.put(QuestionTable.COLUMN_IS_ANSWERED, Queries.FALSE);
                    // insert this values
                    Log.d(TAG, "Added to database element, id = " + db.insert(QuestionTable.TABLE_NAME, null, cv));
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

    // this method is invoked by getWritableDatabase()
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate database " + QuestionTable.TABLE_NAME);
        db.execSQL(QuestionTable.CREATE);
        int db_size = fillTable(db);
        App.setTotalQuizzes(db_size);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade SQL Database: old version = " + oldVersion + " new version = " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(db);
    }

}
