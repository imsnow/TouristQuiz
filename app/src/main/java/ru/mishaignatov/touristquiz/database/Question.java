package ru.mishaignatov.touristquiz.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.mishaignatov.touristquiz.Utils;
import ru.mishaignatov.touristquiz.game.App;

/**
 * Created by Mike on 05.12.15.
 *
 */
@DatabaseTable(tableName = "questions")
public class Question {

    public static final int PLUS_MILLIS = 30;
    public static final int MINUS_MILLIS = 10;

    public static final String COLUMN_LEVEL_ID    = "level_id";
    public static final String COLUMN_IS_ANSWERED = "is_answered";
    public static final String COLUMN_IS_SHOWN    = "is_shown";

    @DatabaseField(id = true)
    public int id;
    @DatabaseField
    public String quiz;
    @DatabaseField
    public String answers;
    @DatabaseField(columnName = COLUMN_LEVEL_ID)
    public int level_id;
    @DatabaseField
    public String type;
    @DatabaseField(columnName = COLUMN_IS_ANSWERED)
    public boolean is_answered;
    @DatabaseField(columnName = COLUMN_IS_SHOWN)
    public boolean is_shown;

    public int attempt = 3;

    // true if user has attempt
    public boolean minusAttempt(){
        if(attempt == 1) return false;
        attempt--;
        return true;
    }

    Question(){}

    public List<String> getRandomListAnswers(){
        //return Utils.shuffleStringArray(getListAnswers());
        return Utils.shuffleList(getListAnswers());
    }

    private List<String> getListAnswers(){
        List<String> result = new ArrayList<>(4);
        String[] arr = answers.split(",");
        List<String> pre = Arrays.asList(arr);
        // делаем ответы с большой буквы, остальные маленькие
        for(String item : pre){
            String s = Utils.doAnswerString(item);
            result.add(s);
        }
        return result;
    }

    public int getType() {
        switch (type) {
            case "attraction":
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

    public boolean isAnswer(final String choice){
        String answer = getListAnswers().get(0).trim();
        return answer.equals(choice);
    }

    public void setAnswered(){
        App.getDbHelper().getQuestionDao().setQuestionAnswered(this);
    }

    public int calcScore(long timeInMils){
        float k = 1f;
        switch (attempt){
            case 3: k = 1f; break;
            case 2: k = 0.7f; break;
            case 1: k = 0.3f; break;
        }
        return (int)(k * (100 + 100*1000/timeInMils));
    }
}
