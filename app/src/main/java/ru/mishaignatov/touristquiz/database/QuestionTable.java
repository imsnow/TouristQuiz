package ru.mishaignatov.touristquiz.database;

import android.provider.BaseColumns;

/**
 * Created by Ignatov on 18.08.2015.
 */
public interface QuestionTable {

    String NAME = "questions";

    String COLUMN_ID      = BaseColumns._ID;
    String COLUMN_QUIZ    = "quiz";
    String COLUMN_ANSWERS = "answers";
    String COLUMN_COUNTRY = "country";
    String COLUMN_TYPE    = "type";

    String PROJECTION[] = new String[]{
            COLUMN_ID, COLUMN_QUIZ, COLUMN_ANSWERS, COLUMN_COUNTRY, COLUMN_TYPE};

    String CREATE = "CREATE TABLE " + NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_QUIZ + " TEXT NOT NULL, " +
            COLUMN_ANSWERS + " TEXT NOT NULL, " +
            COLUMN_COUNTRY + " TEXT NOT NULL, " +
            COLUMN_TYPE + " TEXT NOT NULL );";
}
