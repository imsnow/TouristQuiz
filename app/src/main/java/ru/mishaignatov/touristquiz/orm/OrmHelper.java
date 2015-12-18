package ru.mishaignatov.touristquiz.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Mike on 03.12.15.
 *
 */
public class OrmHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "database.db";

    private static final int DB_VERSION = 1;

    public OrmHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Country.class);
            TableUtils.createTable(connectionSource, Questions.class);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    // create Country Dao
    public RuntimeExceptionDao<Country, Integer> getCountryDao(){
        return getRuntimeExceptionDao(Country.class);
    }

    public RuntimeExceptionDao<Questions, Integer> getQuestionDao(){
        return getRuntimeExceptionDao(Questions.class);
    }
}
