package ru.mishaignatov.touristquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;

import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.ui.DialogHelper;

/**
 * Created by Ignatov on 09.09.2015.
 *
 */
public class GameManager {

    private static final String TAG = "GameManager";
    private static final String PREFS_NAME   = "quiz";

    private static final String KEY_TOTAL    = "total";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_MILES    = "miles";
    private static final String KEY_SCORE    = "score";

    public static final int QUESTION_TIME = 10;

    private SharedPreferences prefs;

    private int total_size;          // Сколько всего загадок находится в файле
    private int answered_size;       // Сколько пользователь отгадал загадок
    private int miles;               // Зработанные мили пользователя
    private int score;               // Заработанные очки пользователя

    //private Question mCurrentQuestion = null; // текущий вопрос пользователя
    private int mCurrentCountryId = 0;

    private static GameManager instance = null;
    private Context mContext = null;

    private GameManager(Context context) {
        mContext = context;
        loadPreference();
    }

    public static GameManager getInstance(Context context){
        if(instance == null) instance = new GameManager(context);
        return instance;
    }


    public Question getQuestion(){
        return OrmDao.getInstance(mContext).getRandomQuestion(mCurrentCountryId);
    }

    private void loadPreference(){
        prefs = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        total_size      = prefs.getInt(KEY_TOTAL,    0);
        answered_size   = prefs.getInt(KEY_ANSWERED, 0);
        miles           = prefs.getInt(KEY_MILES,    0);
        score           = prefs.getInt(KEY_SCORE,    0);
    }

    public void savePreference(){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(KEY_TOTAL, total_size);
        edit.putInt(KEY_ANSWERED, answered_size);
        edit.putInt(KEY_MILES, miles);
        edit.putInt(KEY_SCORE, score);
        edit.apply();

        Log.d(TAG, "Preference saved, total = " + total_size + " answered = " + answered_size + " score = " + score);
    }

    public void userAnswered(Fragment fragment, Question question,
                             String answer, int time, DialogInterface.OnClickListener listener){

        boolean result = Question.isAnswer(question, answer);
        if (result) {
            int tempScore = 50 + 50*time/QUESTION_TIME;
            score += tempScore;
            miles += 30;
            DialogHelper.showDialogSuccess(fragment.getActivity(), time, tempScore, 30, listener);
            OrmDao.getInstance(mContext).setQuestionAnswered(question);
        } else
            DialogHelper.showDialogFailure(fragment.getActivity(), listener);

    }

    public void setCurrentCountryId(int mCurrentCountryId) {
        this.mCurrentCountryId = mCurrentCountryId;
    }

    public int getTotal()           {  return OrmDao.getInstance(mContext).getSizeOfQuestions(); }
    public int getAnswered()        {  return OrmDao.getInstance(mContext).getSizeOfAnswered();  }
    public int getMiles()           {  return miles; }
    public int getScore()           {  return score; }

    public String getStatusTip(){
        return "Вопосы: отвеченные = " + getAnswered() + ", всего = " + getTotal()
                + "\nочки = " + getScore() + " мили = " +  getMiles();
    }
}
