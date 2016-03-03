package ru.mishaignatov.touristquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/***
 * Created by Mike on 01.03.2016.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "tq.db";
    private static final int DB_VERSION = 1;

//

    private LevelDao mLevelDao;
    private QuestionDao mQuestionDao;
    private AchievementDao mAchievementDao;
    private TipDao mTipDao;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //mContext = context;
        mLevelDao = LevelDao.getInstance(this, context);
        mQuestionDao = QuestionDao.getInstance(this, context);
        mAchievementDao = AchievementDao.getInstance(this, context);
        mTipDao = TipDao.getInstance(this, context);
    }

    public LevelDao getLevelDao(){
        return mLevelDao;
    }
    public QuestionDao getQuestionDao() { return mQuestionDao; }
    public AchievementDao getAchievementDao() { return mAchievementDao; }
    public TipDao getTipDao() { return mTipDao; }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try{
            TableUtils.createTable(connectionSource, Level.class);
            TableUtils.createTable(connectionSource, Question.class);
            TableUtils.createTable(connectionSource, Achievement.class);
            TableUtils.createTable(connectionSource, Tip.class);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        mLevelDao.fillTable();
        mQuestionDao.fillTable();

        mLevelDao.calcTotalQuestion();

        mTipDao.createTipsEntries();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}