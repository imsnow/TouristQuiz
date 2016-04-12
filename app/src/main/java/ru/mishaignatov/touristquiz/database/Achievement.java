package ru.mishaignatov.touristquiz.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * Created by Leva on 26.02.2016.
 */
@DatabaseTable(tableName = "achievements")
public class Achievement {

    Achievement() {}

    @DatabaseField(id = true, index = true)
    public int id;
    //@DatabaseField
    //public String title;
    @DatabaseField
    public String content;
    @DatabaseField
    public int draw_resource;
}
