package ru.mishaignatov.touristquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Leva on 12.12.2015.
 */
public class TabelFiller {

    String[] filesArr = new String[] {
            "Франция.txt", "США.txt", "Испания.txt"
    };

    public TabelFiller() {

    }

    public int fillTable(SQLiteDatabase db, Context context){

        int quiz_cnt = 0;

        for(int i=0; i<filesArr.length; i++){
            quiz_cnt += readFile(context, db, filesArr[i]);
        }

        return quiz_cnt;
    }

    private int readFile(Context context, SQLiteDatabase db, String filename){

        // read Questions From FILE
        AssetManager assets = context.getAssets();
        InputStream is;
        int cnt = 0;
        try {
            is = assets.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                String[] arr = s.split(";");
                if (arr.length == 3) {
                    ContentValues cv = new ContentValues();
                    // Fill contentValues
                    cv.put(QuestionTable.COLUMN_QUIZ, arr[0].trim());
                    cv.put(QuestionTable.COLUMN_ANSWERS, arr[1].trim());
                    cv.put(QuestionTable.COLUMN_COUNTRY, filename);
                    cv.put(QuestionTable.COLUMN_TYPE, arr[2].trim());
                    cv.put(QuestionTable.COLUMN_IS_ANSWERED, Queries.FALSE);
                    // insert this values
                    Log.d("TAG", "Added to database element, id = " + db.insert(QuestionTable.TABLE_NAME, null, cv));
                    //db.insert(QuestionTable.NAME, null, cv);
                    // calculate all new questions
                    cnt++;
                }
                else Log.d("TAG", "Error in line = " + (cnt+1) + " string = " + s);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return cnt;
    }
}
