package ru.mishaignatov.touristquiz.database;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

/***
 * Created by Mike on 01.03.2016.
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "tq.db";
    private static final int DB_VERSION = 1;


    private Context mContext;

    private LevelDao mLevelDao;
    private QuestionDao mQuestionDao;
    private AchievementDao mAchievementDao;
    private TipDao mTipDao;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        mLevelDao = LevelDao.getInstance(this, context);
        mQuestionDao = QuestionDao.getInstance(this, context);
        mAchievementDao = AchievementDao.getInstance(this, context);
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

        fillDatabase();

        mTipDao.createTipsEntries();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


    private void fillDatabase(){
        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open("questions.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while((s = reader.readLine()) != null){
                String[] row = s.split(";");
                if(row.length == 7){

                    String levelName = row[0].trim();
                    Level level = mLevelDao.searchLevel(levelName);
                    if(level == null) {
                        level = new Level();
                        level.name = levelName;
                        level.questions_total    = 0;
                        level.questions_answered = 0;
                        level.questions_shown    = 0;
                        level.is_ended = false;
                        level.is_opened = true;
                        level.cost = 0;
                        mLevelDao.createLevel(level);
                    }

                    Question question = new Question();
                    question.quiz    = row[1].trim();
                    question.answers = row[3].trim();
                    question.type    = row[6].trim();
                    question.is_answered = false;
                    question.is_shown    = false;
                    question.level_id = level.id;
                    mQuestionDao.create(question);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
