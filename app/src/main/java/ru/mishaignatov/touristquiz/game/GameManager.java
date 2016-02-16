package ru.mishaignatov.touristquiz.game;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;

import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.ui.DialogHelper;

/**
 * Created by Ignatov on 09.09.2015.
 *
 */
public class GameManager {

    private static final String TAG = "GameManager";

    private User user;
    private OrmDao ormDao;

    private static GameManager instance = null;
    private Context mContext = null;

    private GameManager(Context context) {
        mContext = context;
        user = User.getUser(context);
        ormDao = OrmDao.getInstance(context);
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
    public int getTotal()           {  return OrmDao.getInstance(mContext).getSizeOfQuestions(); }
    // Сколько пользователь отгадал загадок
    public int getAnswered()        {  return OrmDao.getInstance(mContext).getSizeOfAnswered();  }

    public Question getQuestion(int countryId){
        return OrmDao.getInstance(mContext).getRandomQuestion(countryId);
    }

    /*
    public void setCurrentCountryId(int mCurrentCountryId) {
        this.mCurrentCountryId = mCurrentCountryId;
    }
    */
    public String getStatusTip(){
        return "Вопосы: отвеченные = " + getAnswered() + ", всего = " + getTotal()
                + "\nочки = " + user.getScores() + " мили = " +  user.getMillis();
    }
}
