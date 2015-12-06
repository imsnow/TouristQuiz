package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 05.12.15.
 *
 */
@DatabaseTable(tableName = "questions")
public class Questions {

    @DatabaseField(id = true)
    private int Id;

    @DatabaseField
    private String quiz;

    @DatabaseField
    private String answers;

    @DatabaseField(foreign = true)
    private Country country;

    @DatabaseField
    private int type;
}
