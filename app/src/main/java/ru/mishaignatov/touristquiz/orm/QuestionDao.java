package ru.mishaignatov.touristquiz.orm;

import android.content.Context;
import android.content.res.AssetManager;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/***
 * Created by Leva on 01.03.2016.
 */
public class QuestionDao {

    private Context mContext;
    private RuntimeExceptionDao<Question, Integer> mQuestionDao;

    private static QuestionDao INSTANCE;
    public static QuestionDao getIstance(Context context){
        if(INSTANCE == null) INSTANCE = new QuestionDao(context);
        return INSTANCE;
    }

    private QuestionDao(Context context){
        mContext = context;
        OrmHelper helper = new OrmHelper(context);
        mQuestionDao = helper.getQuestionDao();
    }

    public void createQuestionsEntries(){

        AssetManager am = mContext.getAssets();
        String[] row;
        try {
            InputStream is = am.open("questions.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null){
                row = s.split(";");

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
