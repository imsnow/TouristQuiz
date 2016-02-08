package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 03.12.15.
 *
 */
@DatabaseTable(tableName = "countries")
public class Country {

    public static final String COLUMN_COUNTRY_NAME = "value";

    @DatabaseField(id = true, index = true)
    public int id;
    @DatabaseField
    public String filename;
    @DatabaseField
    public String value;

    // Questions fields
    @DatabaseField
    public int total;
    @DatabaseField
    public int answered;
    @DatabaseField
    public int shown;

    // purchase field
    @DatabaseField
    public boolean ended;
    @DatabaseField
    public boolean opened;
    @DatabaseField
    public int cost;

    Country(){}

    @Override
    public String toString() {
        return "Country{" +
                "answered=" + answered +
                ", id=" + id +
                ", filename='" + filename + '\'' +
                ", value='" + value + '\'' +
                ", total=" + total +
                ", shown=" + shown +
                ", ended=" + ended +
                ", opened=" + opened +
                ", cost=" + cost +
                '}';
    }
}
