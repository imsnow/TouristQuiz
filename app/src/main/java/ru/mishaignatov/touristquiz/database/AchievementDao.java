package ru.mishaignatov.touristquiz.database;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

/***
 * Created by Leva on 26.02.2016.
 */
public class AchievementDao {

    private static AchievementDao instance = null;

    private RuntimeExceptionDao<Achievement, Integer> mDao;

    public static AchievementDao getInstance(DbHelper helper, Context context){
        if(instance == null) instance = new AchievementDao(helper, context);
        return instance;
    }
    private AchievementDao(DbHelper helper, Context context){
        mDao = helper.getRuntimeExceptionDao(Achievement.class);
    }

    public int createEntries(){
        return 0;
    }
}
