package ru.mishaignatov.touristquiz.orm;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import ru.mishaignatov.touristquiz.R;

/**
 * Created by Leva on 17.12.2015.
 *
 */
public class OrmDao {

    private static final String[] filesArr = new String[] {
            "france.txt", "usa.txt", "spain.txt",
            "china.txt", "italy.txt", "turkey.txt",
            "germany.txt", "england.txt", "russia.txt",
            "mexico.txt", "thailand.txt"
    };

    private static OrmDao instance = null;

    private Context context = null;
    private OrmHelper helper = null;
    private RuntimeExceptionDao<Country, Integer> mCountryDao;
    private RuntimeExceptionDao<Question, Integer> mQuestionDao;
    private RuntimeExceptionDao<Tip, Integer> mTipDao;

    private Random random = new Random();

    private OrmDao(Context context){
        this.context = context;
        helper = new OrmHelper(context);
        mCountryDao = helper.getCountryDao();
        mQuestionDao = helper.getQuestionDao();
        mTipDao      = helper.getTipsDao();
    }

    public static OrmDao getInstance(Context context) {
        if(instance == null)
            instance = new OrmDao(context);
        return instance;
    }

    public Question getRandomQuestion(int country_id){
        try {
            PreparedQuery<Question> preparedQuery = mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_COUNTRY, country_id)
                    .and()
                    .eq(Question.COLUMN_IS_SHOWN, false)
                    .prepare();

            List<Question> list = mQuestionDao.query(preparedQuery);
            if(list != null && list.size() != 0){
                int randIndex = random.nextInt(list.size());
                return list.get(randIndex);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean isCountryShown(int country_id){

        try {
            int size = (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_COUNTRY, country_id)
                    .and()
                    .eq(Question.COLUMN_IS_SHOWN, false)
                    .countOf();
            return (size == 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setQuestionShown(Question question){
        question.is_shown = true;
        mQuestionDao.update(question);
    }

    // When user answered right
    public void setQuestionAnswered(Question question) {
        question.is_answered = true;
        mQuestionDao.update(question);
        // TODO
    }

    // calculate all rows
    public int getSizeOfQuestions() {
        return (int)mQuestionDao.countOf();
    }

    // calculate answered rows
    public int getSizeOfAnswered() {
        try {
            return (int) mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_IS_ANSWERED, true)
                    .countOf();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    /*
    public List<Question> getQuestionsList(int country_id){

        try {
            return mQuestionDao.query(mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_COUNTRY, country_id)
                    .prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    */
    public List<Country> getCountryList(){
        return mCountryDao.queryForAll();
    }

    //public void updateCountry(int country_id){
    public Country updateCountry(Country country){
        try {
            // size of answered question
            int size = (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_COUNTRY, country.id)
                    .and()
                    .eq(Question.COLUMN_IS_ANSWERED, true)
                    .countOf();

            int size_shown = (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_COUNTRY, country.id)
                    .and()
                    .eq(Question.COLUMN_IS_SHOWN, true)
                    .countOf();

            Log.d("TAG", "size answered = " + size + " size shown = " + size_shown);
            country.answered = size;
            country.shown    = size_shown;
            mCountryDao.update(country);

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return country;
    }

    public String getCountryName(int id){
        try {
            return mCountryDao.queryBuilder().where().
                    eq("id", id).queryForFirst().value;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public int createCountryEntries(){

        String[] values = context.getResources().getStringArray(R.array.countries);
        String[] costs = context.getResources().getStringArray(R.array.cost_array);
        int count = 0;
        for(int i=0; i<filesArr.length; i++) {
            Country country = new Country();
            country.id = i;
            country.filename = filesArr[i];
            country.value = values[i];
            country.total = 0;
            country.answered = 0;
            country.shown = 0;
            country.cost = Integer.parseInt(costs[i]);
            if(country.cost != 0)
                country.opened = false;
            else
                country.opened = true;
            country.ended = false;
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
                    if(arr.length == 3) {

                        Question q = new Question();
                        q.quiz = arr[0].trim();
                        q.answers = arr[1].trim();
                        q.type = arr[2].trim();
                        q.country_id = item.id;
                        q.is_answered = false; // 0 - false, 1 - true
                        q.is_shown = false;
                        mQuestionDao.create(q);
                        all_questions_cnt++;
                        cnt++;
                    }
                    else Log.d("TAG", "error element, string = " + s);
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
    //===========================================================================
    // TIPS
    public int createTipsEntries(){
        int count = 0;

        String[] tipsArr = context.getResources().getStringArray(R.array.tips);

        for(int i = 0; i<tipsArr.length; i++){
            Tip tip = new Tip();
            tip.id   = i;
            tip.text = tipsArr[i];
            mTipDao.create(tip);
            count++;
        }
        return count;
    }

    public List<Tip> getTipsList(){
        return mTipDao.queryForAll();
    }
}
