package ru.mishaignatov.touristquiz.database;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.Arrays;
import java.util.List;

import ru.mishaignatov.touristquiz.R;

/***
 * Created by Leva on 26.02.2016.
 */
public class AchievementDao {

    private static AchievementDao instance = null;
    private Context context;

    private RuntimeExceptionDao<Achievement, Integer> mDao;

    public static AchievementDao getInstance(DbHelper helper, Context context){
        if(instance == null) instance = new AchievementDao(helper, context);
        return instance;
    }
    private AchievementDao(DbHelper helper, Context context){
        mDao = helper.getRuntimeExceptionDao(Achievement.class);
        this.context = context;
    }

    public int createEntries(){

        String[] contents = context.getResources().getStringArray(R.array.achievements);

        for (int i=0; i<contents.length; i++) {
            Achievement achievement = new Achievement();
            achievement.id = i;
            achievement.content = contents[i];
            achievement.draw_resource = R.drawable.ic_best;

            mDao.create(achievement);
        }

        return 0;
    }

    public List<Achievement> getAllList() {
        return mDao.queryForAll();
    }
}
