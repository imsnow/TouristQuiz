package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/***
 * Created by Leva on 16.02.2016.
 */
@DatabaseTable(tableName = "tips")
public class Tip {

    public Tip(){}

    @DatabaseField(id = true, index = true)
    public int id;

    @DatabaseField
    public String text;
}
