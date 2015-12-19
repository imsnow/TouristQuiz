package ru.mishaignatov.touristquiz.orm;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import ru.mishaignatov.touristquiz.Utils;

/**
 * Created by Mike on 05.12.15.
 *
 */
@DatabaseTable(tableName = "questions")
public class Question {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String quiz;
    @DatabaseField
    public String answers;
    @DatabaseField //(foreign = true)
    public int country_id;
    @DatabaseField
    public String type;

    Question(){}

    public String[] getRandomListAnswers(){
        return Utils.shuffleStringArray(getListAnswers());
    }

    private String[] getListAnswers(){
        return answers.split(",");
    }

    public int getType()                   {
        switch (type) {
            case "lions":
                return 0;
            case "kitchen":
                return 1;
            case "geo":
                return 2;
            case "history":
                return 3;
        }
        return 0;
    }
}
