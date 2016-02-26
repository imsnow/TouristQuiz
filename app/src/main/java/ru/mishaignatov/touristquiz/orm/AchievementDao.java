package ru.mishaignatov.touristquiz.orm;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

/***
 * Created by Leva on 26.02.2016.
 */
public class AchievementDao {

    private static AchievementDao instance = null;

    private RuntimeExceptionDao<Achievement, Integer> mDao;

    public static AchievementDao getInstance(Context context){
        if(instance == null) instance = new AchievementDao(context);
        return instance;
    }
    private AchievementDao(Context context){
        mDao = new OrmHelper(context).getAchievementDao();
    }

    public int createEntries(){
        return 0;
    }
}
