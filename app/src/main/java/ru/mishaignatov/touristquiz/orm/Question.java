package ru.mishaignatov.touristquiz.orm;

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

    public static final int MILLIS = 30;

    public static final String COLUMN_COUNTRY     = "country_id";
    public static final String COLUMN_IS_ANSWERED = "is_answered";

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField
    public String quiz;
    @DatabaseField
    public String answers;
    @DatabaseField (columnName = COLUMN_COUNTRY)
    public int country_id;
    @DatabaseField
    public String type;
    @DatabaseField(columnName = COLUMN_IS_ANSWERED)
    public boolean is_answered;

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

    public static boolean isAnswer(final Question quiz, final String choice){
        String answer = quiz.getListAnswers().get(0).trim();
        return answer.equals(choice);
    }

    public void setAnswered(){
        OrmDao.getInstance(App.getContext()).setQuestionAnswered(this);
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
