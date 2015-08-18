package ru.mishaignatov.touristquiz.database;

import android.provider.BaseColumns;

/**
 * Created by Ignatov on 18.08.2015.
 */
public interface CountryTable {

    String NAME = "countries";

    String COL_ID      = BaseColumns._ID;
    String COL_COUNTRY = "country";

    String POJECTION[] = new String[]{ COL_ID, COL_COUNTRY};

    String CREATE = "CREATE TABLE " + NAME + " (" +
            COL_ID + " INTEGER PRIMARY KEY, " +
            COL_COUNTRY + " TEXT NOT NULL" + ");";
}
