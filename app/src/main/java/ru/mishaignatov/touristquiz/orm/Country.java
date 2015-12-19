package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 03.12.15.
 *
 */
@DatabaseTable(tableName = "countries")
public class Country {

    @DatabaseField(id = true, index = true)
    public int id;
    @DatabaseField
    public String filename;
    @DatabaseField
    public String value;
    @DatabaseField
    public int total;
    @DatabaseField
    public int answered;
    @DatabaseField
    public int cost;

    Country(){}

    @Override
    public String toString() {
        return "Country = " + value + " answered = " + answered + " total = " + total + " id = " + id;
    }
}
