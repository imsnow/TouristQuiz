package ru.mishaignatov.touristquiz.database;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

import ru.mishaignatov.touristquiz.server.APIStrings;

/***
 * Created by Mike on 01.03.2016.
 */
public class LevelDao {

   // private Context mContext;
    private DbHelper mHelper;
    private RuntimeExceptionDao<Level, Integer> mLevelDao;

    private static LevelDao INSTANCE;
    public static LevelDao getInstance(DbHelper helper, Context context){
        if(INSTANCE == null) INSTANCE = new LevelDao(helper, context);
        return INSTANCE;
    }

    private LevelDao(DbHelper helper, Context context){
        //mContext = context;
        mHelper = helper;
        mLevelDao = helper.getRuntimeExceptionDao(Level.class);
    }

    public Level searchLevel(String name){

        try {
            return mLevelDao.queryBuilder().where().eq(APIStrings.NAME, name).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createLevel(Level level){
        mLevelDao.create(level);
    }

    public Level getLevelById(int id){
        try {
            return mLevelDao.queryBuilder().where().
                    eq("id", id).queryForFirst();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Level> getLevelList(){
        return mLevelDao.queryForAll();
    }

    public void openLevel(Level level){
        level.is_opened = true;
        mLevelDao.update(level);
    }

    public Level updateLevel(Level level){
        // size of answered question
        int size = mHelper.getQuestionDao().getAnsweredCount(level.id);
        // size of shown question
        int size_shown = mHelper.getQuestionDao().getShownCount(level.id);
        // all size question
        int total = mHelper.getQuestionDao().getTotalcount(level.id);

        level.questions_answered = size;
        level.questions_shown    = size_shown;

        if (level.questions_shown == total)
            level.is_ended = true;
        mLevelDao.update(level);

        return level;
    }
}
