package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;

/**
 * Created by Mike on 05.12.15.
 *
 */
public class CountryDao extends BaseDaoImpl<Country, Integer> {


    protected CountryDao(Class<Country> dataClass) throws SQLException {
        super(dataClass);
    }

    protected CountryDao(ConnectionSource connectionSource, Class<Country> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected CountryDao(ConnectionSource connectionSource, DatabaseTableConfig<Country> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }
}
