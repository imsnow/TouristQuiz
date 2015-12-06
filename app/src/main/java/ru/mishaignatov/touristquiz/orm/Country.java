package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Mike on 03.12.15.
 *
 */
@DatabaseTable(tableName = "countries")
public class Country {

    @DatabaseField(generatedId = true, id = true)
    private int id;

    @DatabaseField
    private String en;

    @DatabaseField
    private String ru;


}
