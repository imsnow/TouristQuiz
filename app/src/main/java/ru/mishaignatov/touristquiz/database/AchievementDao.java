package ru.mishaignatov.touristquiz.database;

import android.content.Context;

import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
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
            achievement.isAchieved = false;
            mDao.create(achievement);
        }

        return 0;
    }

    public List<Achievement> getAllList() {
        return mDao.queryForAll();
    }

    public Achievement getAchievementById(int id) {
        try {
            return mDao.queryBuilder().where().eq("id", id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setAchieved(Achievement achievement, boolean isAchieved) {
        achievement.isAchieved = isAchieved;
        mDao.update(achievement);
    }
}
