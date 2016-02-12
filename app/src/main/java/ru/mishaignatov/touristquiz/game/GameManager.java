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

    public static final int QUESTION_TIME = 10;
    private static final int QUESTION_MILES = 30;

    //private Question mCurrentQuestion = null; // текущий вопрос пользователя
    private int mCurrentCountryId = 0;

    private static GameManager instance = null;
    private Context mContext = null;
    //private ActivityInterface activityInterface;

    private GameManager(Context context) {
        mContext = context;
        user = User.getUser(context);
        ormDao = OrmDao.getInstance(context);
        //activityInterface = (MainActivity)mContext;
    }

    public static GameManager getInstance(Context context){
        if(instance == null) instance = new GameManager(context);
        return instance;
    }

    public void startGame(){
        //if(user.isRegistered() && user.getToken().length() > 0)
        //    activityInterface.onStartFragment();
        //else
        //    activityInterface.onLoadFragment();
    }


    public void makeSnapshot(){
        user.saveUser();
    }

    public User getUser(){
        return user;
    }

    public OrmDao getOrmDao(){
        return ormDao;
    }

    // Сколько всего загадок находится в файле
    public int getTotal()           {  return OrmDao.getInstance(mContext).getSizeOfQuestions(); }
    // Сколько пользователь отгадал загадок
    public int getAnswered()        {  return OrmDao.getInstance(mContext).getSizeOfAnswered();  }

    public Question getQuestion(){
        return OrmDao.getInstance(mContext).getRandomQuestion(mCurrentCountryId);
    }

    public void userAnsweredTrue(Fragment fragment, Question question,
                             String answer, int time, DialogInterface.OnClickListener listener){

        int tempScore = 50 + 50*time/QUESTION_TIME;
        user.addResult(tempScore, QUESTION_MILES);
        DialogHelper.showDialogSuccess(fragment.getActivity(), time, tempScore, QUESTION_MILES, listener);
        //OrmDao.getInstance(mContext).setQuestionAnswered(question);

            //DialogHelper.showDialogFailure(fragment.getActivity(), listener);

    }

    public void setCurrentCountryId(int mCurrentCountryId) {
        this.mCurrentCountryId = mCurrentCountryId;
    }

    public String getStatusTip(){
        return "Вопосы: отвеченные = " + getAnswered() + ", всего = " + getTotal()
                + "\nочки = " + user.getScores() + " мили = " +  user.getMillis();
    }
}
