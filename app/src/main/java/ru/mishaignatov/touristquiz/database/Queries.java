package ru.mishaignatov.touristquiz.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import ru.mishaignatov.touristquiz.data.Country;
import ru.mishaignatov.touristquiz.data.Quiz;

/**
 * Created by Ignatov on 10.09.2015.
 * Class store all queries to database
 * All methods is static
 */
public class Queries {

    private static final String TAG = "Queries";

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    //=============================================================
    // Queries to DataBase
    // return all existing countries form database
    public static ArrayList<String> getCountryList(SQLiteDatabase db){
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
    /*
    // Return all quizzes for special country
    // only don't unravel questions
    public static ArrayList<Quiz> getQuizzesList(SQLiteDatabase db, String country){
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
    */
    public static Quiz getRandomQuiz(SQLiteDatabase db, String country) {

        Quiz q = null;

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                QuestionTable.TAKE_QUIZZES,
                QuestionTable.COLUMN_COUNTRY + " = ? AND " + QuestionTable.COLUMN_IS_ANSWERED + " = ?",
                new String[]{country, String.valueOf(FALSE) }, null, null, null);

        if(c != null) {
            int size = c.getCount();
            if(size == 0) return null; // Questions ended
            Random r = new Random();
            int random = r.nextInt(size);

            c.moveToPosition(random);

            q = new Quiz(c.getString(0), c.getString(1), c.getString(2));

            Log.d(TAG, "Random quiz = " + random + " from = " + size);
        }

        return q;
    }

    // Load country info from database
    public static Country loadCountry(SQLiteDatabase db, String name){

        int total = 0, answered = 0;

        Cursor c = db.query(QuestionTable.TABLE_NAME,
                new String[] { QuestionTable.COLUMN_COUNTRY, QuestionTable.COLUMN_IS_ANSWERED},
                QuestionTable.COLUMN_COUNTRY + " = ?",
                new String[]{name},null,null,null);

        if(c != null && c.moveToFirst()){
            do {
                int is_answered = Integer.parseInt(c.getString(1));
                if (is_answered == TRUE) answered++;
                total++;

            } while (c.moveToNext());
        }
        c.close();

        Country country = new Country(name, answered, total);
        return country;
    }

    public static void setQuestionAnswered(SQLiteDatabase db, Quiz quiz, String country){

        ContentValues cv = new ContentValues();
        cv.put(QuestionTable.COLUMN_QUIZ, quiz.getText());
        cv.put(QuestionTable.COLUMN_ANSWERS, quiz.getStringAnswers());
        cv.put(QuestionTable.COLUMN_COUNTRY, country);
        cv.put(QuestionTable.COLUMN_TYPE, quiz.getType());
        cv.put(QuestionTable.COLUMN_IS_ANSWERED, Queries.TRUE);

        int i = db.update(QuestionTable.TABLE_NAME, cv,
                QuestionTable.COLUMN_QUIZ + " = ?",
                new String[]{quiz.getText()});

        Log.d(TAG, "Quiz n = " + i + " updated");
    }
    /*
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
            }

            Country country = new Country(item, no_answered, total);
            countries.add(country);
        }

        c.close();

        for(Country item : countries)
            Log.d(TAG, item.getName() + " " + item.getNoAnswered() + "/"  + item.getTotal());
    }
    */
}
