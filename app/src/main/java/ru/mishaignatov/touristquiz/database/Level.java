package ru.mishaignatov.touristquiz.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 01.03.2016.
 **/
@DatabaseTable(tableName = "levels")
public class Level {

    public Level(){}

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String name;

    @DatabaseField
    public int questions_total;
    @DatabaseField
    public int questions_answered;
    @DatabaseField
    public int questions_shown;

    @DatabaseField
    public boolean is_ended;
    @DatabaseField
    public boolean is_opened;
    @DatabaseField
    public int cost;

    @Override
    public String toString() {
        return "Level{" +
                "cost=" + cost +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", questions_total=" + questions_total +
                ", questions_answered=" + questions_answered +
                ", questions_shown=" + questions_shown +
                ", is_ended=" + is_ended +
                ", is_opened=" + is_opened +
                '}';
    }
}
