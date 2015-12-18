package ru.mishaignatov.touristquiz.orm;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Leva on 17.12.2015.
 *
 */
public class OrmDao {

    private static final String[] filesArr = new String[] {
            "france.txt", "usa.txt", "spain.txt"
    };

    private static OrmDao instance = null;

    private Context context = null;
    private OrmHelper helper = null;
    private RuntimeExceptionDao<Country, Integer> mCountryDao;
    private RuntimeExceptionDao<Questions, Integer> mQuestionDao;

    private OrmDao(Context context){
        this.context = context;
        helper = new OrmHelper(context);
        mCountryDao = helper.getCountryDao();
        mQuestionDao = helper.getQuestionDao();
    }

    public static OrmDao getInstance(Context context) {
        if(instance == null)
            instance = new OrmDao(context);
        return instance;
    }

    public List<Country> getCountryList(){
        return mCountryDao.queryForAll();
    }

    public int createCountryEntries(){

        String[] values = context.getResources().getStringArray(R.array.countries);
        int count = 0;
        for(int i=0; i<filesArr.length; i++) {
            Country country = new Country();
            country.id = i;
            country.filename = filesArr[i];
            country.value = values[i];
            country.total = 0;
            country.answered = 0;
            country.cost = 0;
            mCountryDao.create(country);
            count++;
        }
        return count;
    }

    public int createQuestionEntries(){

        List<Country> list = getCountryList();
        int all_questions_cnt = 0;

        AssetManager assets = context.getAssets();

        for(Country item : list){
            int cnt = 0; // questions this Country
            String filename = item.filename;
            try{
                InputStream is = assets.open(filename);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String s;
                while ((s = reader.readLine()) != null){
                    String[] arr = s.split(";");
                    if(arr.length != 3) {

                        Questions q = new Questions();
                        q.quiz = arr[0].trim();
                        q.answers = arr[1].trim();
                        q.type = arr[2].trim();
                        q.country_id = item.id;
                        mQuestionDao.create(q);
                        all_questions_cnt++;
                        cnt++;
                    }
                    else Log.d("TAG", "Added to database element, string = " + s);
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
            item.total = cnt;
            mCountryDao.update(item);
        }
        return all_questions_cnt;
    }


}
