package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 05.12.15.
 *
 */
@DatabaseTable(tableName = "questions")
public class Questions {

    @DatabaseField(id = true, generatedId = true)
    public int id;
    @DatabaseField
    public String quiz;
    @DatabaseField
    public String answers;
    @DatabaseField(foreign = true)
    public int country_id;
    @DatabaseField
    public String type;

    Questions(){}
}
