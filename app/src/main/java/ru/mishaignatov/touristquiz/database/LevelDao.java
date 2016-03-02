package ru.mishaignatov.touristquiz.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.mishaignatov.touristquiz.server.APIStrings;

/***
 * Created by Mike on 01.03.2016.
 */
public class LevelDao {

    private Context mContext;
    private DbHelper mHelper;
    private RuntimeExceptionDao<Level, Integer> mLevelDao;

    private static LevelDao INSTANCE;
    public static LevelDao getInstance(DbHelper helper, Context context){
        if(INSTANCE == null) INSTANCE = new LevelDao(helper, context);
        return INSTANCE;
    }

    private LevelDao(DbHelper helper, Context context){
        mContext = context;
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
        int total = mHelper.getQuestionDao().getTotalCount(level.id);

        level.questions_answered = size;
        level.questions_shown    = size_shown;

        if (level.questions_shown == total)
            level.is_ended = true;
        mLevelDao.update(level);

        return level;
    }

    public void calcTotalQuestion(){
        List<Level> list = getLevelList();
        for(int i=0; i<list.size(); i++){
            Level level = list.get(i);
            int total = mHelper.getQuestionDao().getTotalCount(level.id);
            level.questions_total = total;
            mLevelDao.update(level);
        }
    }

    public void fillTable(){
        AssetManager am = mContext.getAssets();
        int cnt = 0;
        try {
            InputStream is = am.open("levels.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null) {
                String[] row = s.split(";");
                Level level = new Level();
                level.id = cnt;
                level.name = row[1];
                level.cost = Integer.parseInt(row[3]);
                level.is_opened = level.cost == 0;
                mLevelDao.create(level);
                Log.d("TAG", level.toString());
                cnt++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
