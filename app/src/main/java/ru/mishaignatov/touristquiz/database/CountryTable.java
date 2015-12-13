package ru.mishaignatov.touristquiz.database;

import android.provider.BaseColumns;

/**
 * Created by Mike on 13.12.2015.
 * String of CountryTable
 */
public interface CountryTable {

    String TABLE_NAME = "countries";

    String COLUMN_ID          = BaseColumns._ID;
    String COLUMN_FILE        = "file";
    String COLUMN_VALUE       = "value";

    String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_FILE + " TEXT NOT NULL, " +
            COLUMN_VALUE + " TEXT NOT NULL);";
}
