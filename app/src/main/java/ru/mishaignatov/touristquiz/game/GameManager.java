package ru.mishaignatov.touristquiz.game;

import android.content.Context;

import ru.mishaignatov.touristquiz.database.Question;

/**
 * Created by Ignatov on 09.09.2015.
 *
 */
public class GameManager {

    private static final String TAG = "GameManager";

    private User user;

    private static GameManager instance = null;
    private Context mContext = null;

    private GameManager(Context context) {
        mContext = context;
        user = User.getUser(context);
    }

    public static GameManager getInstance(Context context){
        if(instance == null) instance = new GameManager(context);
        return instance;
    }


    public void makeSnapshot(){
        user.saveUser();
    }

    public User getUser(){
        return user;
    }

    // Сколько всего загадок находится в файле
    public int getTotal()           {  return App.getDbHelper().getQuestionDao().getSizeOfQuestions(); }
    // Сколько пользователь отгадал загадок
    public int getAnswered()        {  return App.getDbHelper().getQuestionDao().getSizeOfAnswered();  }

    public Question getQuestion(int level_id){
        return App.getDbHelper().getQuestionDao().getRandomQuestion(level_id);
    }

    /*
    public void setCurrentCountryId(int mCurrentCountryId) {
        this.mCurrentCountryId = mCurrentCountryId;
    }

    public String getStatusTip(){
        return "Вопосы: отвеченные = " + getAnswered() + ", всего = " + getTotal()
                + "\nочки = " + user.getScores() + " мили = " +  user.getMillis();
    }
    */
}
