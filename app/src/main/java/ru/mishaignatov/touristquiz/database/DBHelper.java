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

    public static final int TRUE = 1;
    public static final int FALSE = 0;

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
                    cv.put(QuestionTable.COLUMN_IS_ANSWERED, FALSE);
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

    //=============================================================
    // Queries to DataBase
    // return all existing countries form database
    public ArrayList<String> getCountryList(SQLiteDatabase db){
        ArrayList<String> list = new ArrayList<>();
        Cursor c = db.query(QuestionTable.TABLE_NAME, new String[]{QuestionTable.COLUMN_COUNTRY}, null, null, QuestionTable.COLUMN_COUNTRY,null,null);
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

    // Return all quizzes for special country
    // only don't unravel questions
    public ArrayList<Quiz> getQuizzesList(SQLiteDatabase db, String country){
        ArrayList<Quiz> list = new ArrayList<>();
        //Cursor c = db.query(QuestionTable.TABLE_NAME, QuestionTable.TAKE_QUIZZES, QuestionTable.COLUMN_COUNTRY + " = ?", new String[]{ country }, null, null, null);
        Cursor c = db.query(QuestionTable.TABLE_NAME,
                            QuestionTable.TAKE_QUIZZES,
                            QuestionTable.COLUMN_COUNTRY + " = ? AND " + QuestionTable.COLUMN_IS_ANSWERED + " = ?",
                            new String[]{ country, String.valueOf(FALSE) }, null, null, null);
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

    public void calcQuizzesForAllCountries(SQLiteDatabase db){
        ArrayList<Country> list = new ArrayList<>();

        String previousCountry = null;
        //Set<String> countriesNames = new HashSet<>();
        int total = 0, no_answered = 0;

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                            new String[] { QuestionTable.COLUMN_COUNTRY, QuestionTable.COLUMN_IS_ANSWERED},
                            null,null,null,null,null);
        if(c != null && c.moveToFirst()){
            do{
                String name = c.getString(0);
                boolean is_answered = Boolean.getBoolean(c.getString(1));
                if(is_answered) no_answered++;
                total++;

                if(!previousCountry.equals(name))

                previousCountry = name;
/*
                if(!name.equals(currentCountry)) {
                    if(currentCountry != null){
                        Country country = new Country(name, no_answered, total);
                        list.add(country);
                        no_answered = 0;
                        total = 0;
                    }
                    currentCountry = name;
                }


*/
            } while (c.moveToNext());
            c.close();
        }
        Log.d(TAG, "Countries List");
        for(Country item : list)
            Log.d(TAG, item.getName() + " " + item.getNoAnswered() + "/"  + item.getTotal());
    }

    public void loadCountries(SQLiteDatabase db, List<String> list){

        ArrayList<Country> countries = new ArrayList<>();

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                new String[] { QuestionTable.COLUMN_COUNTRY, QuestionTable.COLUMN_IS_ANSWERED},
                null,null,null,null,null);

        for(String item : list){

            int total = 0, no_answered = 0;

            if(c != null && c.moveToFirst()) {
                do {
                    String name = c.getString(0);
                    if(name.equals(item)) {
                        boolean is_answered = Boolean.getBoolean(c.getString(1));
                        if (is_answered) no_answered++;
                        total++;
                    }
                } while (c.moveToNext());
                c.close();
            }

            Country country = new Country(item, no_answered, total);
            countries.add(country);
        }
    }
}
