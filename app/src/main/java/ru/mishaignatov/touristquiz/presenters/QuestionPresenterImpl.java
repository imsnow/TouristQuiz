package ru.mishaignatov.touristquiz.presenters;

import android.widget.TextView;

import ru.mishaignatov.touristquiz.database.Question;
import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.Bonus;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.Stopwatch;
import ru.mishaignatov.touristquiz.game.User;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.views.QuestionView;

/**
 * Created by Leva on 28.01.2016.
 **/
public class QuestionPresenterImpl implements QuestionPresenter {

    private GameManager mGameManager;

    private QuestionView questionView;
    private User.ResultInterface resultInterface;

    private Question mCurrentQuestion;
    private Stopwatch mStopwatch;

    private int mCountryId = 0;

    public QuestionPresenterImpl(QuestionView questionView, int country_id, User.ResultInterface resultInterface) {
        mStopwatch = new Stopwatch();
        this.questionView = questionView;
        this.resultInterface = resultInterface;
        mGameManager = GameManager.getInstance(App.getContext());
        mCountryId = country_id;
    }

    @Override
    public void takeQuestion() {
        mCurrentQuestion = mGameManager.getQuestion(mCountryId);
        questionView.setQuestion(mCurrentQuestion);
        if (mCurrentQuestion != null) {
            mStopwatch.start();
        }
    }

    @Override
    public void sendResult() {
        ApiHelper.getHelper(App.getContext()).userResult(mGameManager.getUser(),
                null, null); // TODO Response.Listener<String>
    }

    @Override
    public void onAnswerButtonClick(String answer, TextView button) {

        App.getDbHelper().getQuestionDao().setQuestionShown(mCurrentQuestion);

        if (mCurrentQuestion.isAnswer(answer)) {
            // SUCCESS !!
            mCurrentQuestion.setAnswered();
            long time = mStopwatch.stop();
            int score = mCurrentQuestion.calcScore(time);
            Bonus bonus = mGameManager.getUser().addResult(score, Question.PLUS_MILLIS, mCurrentQuestion.isFirstAttempt(), resultInterface);
            questionView.onTrueAnswer(time, score, Question.PLUS_MILLIS, bonus);
        }
        else { // Try again, buddy
            if(mCurrentQuestion.minusAttempt())
                questionView.onFailAnswer(button);
            else { // You are LOOSER!
                mGameManager.getUser().removeMillis(Question.MINUS_MILLIS);
                questionView.onTotalFailure();
            }
        }
    }

    @Override
    public void onDestroy() {
        questionView = null;
    }

    @Override
    public String getCurrentLevel() {
        //return OrmDao.getInstance(App.getContext()).getCountryName(mCurrentQuestion.country_id);
        return App.getDbHelper().getLevelDao().getLevelById(mCurrentQuestion.level_id).name;
    }

    @Override
    public void setQuestionNotShown() {
        if (mCurrentQuestion != null)
            App.getDbHelper().getQuestionDao().setQuestionNotShown(mCurrentQuestion);
    }
}
