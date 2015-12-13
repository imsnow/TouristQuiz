package ru.mishaignatov.touristquiz.database;

import android.provider.BaseColumns;

/**
 * Created by Ignatov on 18.08.2015.
 * Strings for Question Table
 */
public interface QuestionTable {

    String TABLE_NAME = "questions";

    String COLUMN_ID          = BaseColumns._ID;
    String COLUMN_QUIZ        = "quiz";
    String COLUMN_ANSWERS     = "answers";
    String COLUMN_COUNTRY     = "country";
    String COLUMN_TYPE        = "type";
    String COLUMN_IS_ANSWERED = "is_answered";

    //String PROJECTION[] = new String[]{
    //       COLUMN_ID, COLUMN_QUIZ, COLUMN_ANSWERS, COLUMN_COUNTRY, COLUMN_TYPE};

    String TAKE_QUIZZES[] = new String[] { COLUMN_QUIZ, COLUMN_ANSWERS, COLUMN_TYPE };

    String CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QUIZ + " TEXT NOT NULL, " +
            COLUMN_ANSWERS + " TEXT NOT NULL, " +
            COLUMN_COUNTRY + " INTEGER NOT NULL, FOREIGN KEY" +
            COLUMN_TYPE + " TEXT NOT NULL, " +
            COLUMN_IS_ANSWERED + " INTEGER );";
}
