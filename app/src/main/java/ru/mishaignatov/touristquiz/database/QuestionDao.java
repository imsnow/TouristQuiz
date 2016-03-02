package ru.mishaignatov.touristquiz.database;

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

import ru.mishaignatov.touristquiz.game.App;

/***
 * Created by Leva on 01.03.2016.
 */
public class QuestionDao {

    private Context mContext;
    private RuntimeExceptionDao<Question, Integer> mQuestionDao;
    private Random random = new Random();

    private static QuestionDao INSTANCE;
    public static QuestionDao getInstance(DbHelper helper, Context context){
        if(INSTANCE == null) INSTANCE = new QuestionDao(helper, context);
        return INSTANCE;
    }

    private QuestionDao(DbHelper helper, Context context){
        mContext = context;
        mQuestionDao = helper.getRuntimeExceptionDao(Question.class);
    }

    public void create(Question question){
        mQuestionDao.create(question);
    }

    // When user answered right
    public void setQuestionAnswered(Question question) {
        question.is_answered = true;
        mQuestionDao.update(question);
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

    public Question getRandomQuestion(int level_id){
        try {
            PreparedQuery<Question> preparedQuery = mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_LEVEL_ID, level_id)
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

    public void setQuestionShown(Question question){
        question.is_shown = true;
        mQuestionDao.update(question);
    }

    public int getAnsweredCount(int level_id){
        try {
            return  (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_LEVEL_ID, level_id)
                    .and()
                    .eq(Question.COLUMN_IS_ANSWERED, true)
                    .countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getShownCount(int level_id){
        try {
            return (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_LEVEL_ID, level_id)
                    .and()
                    .eq(Question.COLUMN_IS_SHOWN, true)
                    .countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalCount(int level_id){
        try {
            return (int)mQuestionDao.queryBuilder()
                    .where()
                    .eq(Question.COLUMN_LEVEL_ID, level_id)
                    .countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void fillTable(){

        //List<Level> list = App.getDbHelper().getLevelDao().getLevelList();

        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open("questions.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null) {
                String[] row = s.split(";");

                Question question = new Question();
                question.quiz    = row[1].trim();
                question.answers = row[3].trim();
                question.type    = row[6].trim();
                question.is_answered = false;
                question.is_shown    = false;
                question.level_id = Integer.parseInt(row[0]);
                mQuestionDao.create(question);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
