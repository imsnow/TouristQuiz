package ru.mishaignatov.touristquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/***
 * Created by Mike on 01.03.2016.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "tq.db";
    private static final int DB_VERSION = 10;

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

    public int getDbVersion() { return DB_VERSION; }

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

        fillTables();
    }

    private void fillTables(){
        mLevelDao.fillTable();
        mQuestionDao.fillTable();

        mLevelDao.calcTotalQuestion();

        mTipDao.createTipsEntries();
        mAchievementDao.createEntries();
    }

    // NEED TEST
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        Log.d("TAG", "upgrade database oldV = " + oldVersion + " newVer = " + newVersion);
        if(oldVersion < newVersion){
            mLevelDao.fillTable();
            mQuestionDao.fillTable();
            mLevelDao.calcTotalQuestion();

            try {
                TableUtils.dropTable(connectionSource, Achievement.class, false);
                TableUtils.createTable(connectionSource, Achievement.class);
                mAchievementDao.createEntries();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
