package ru.mishaignatov.touristquiz.presenters;

import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.Stopwatch;
import ru.mishaignatov.touristquiz.orm.OrmDao;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.views.AnswerButton;
import ru.mishaignatov.touristquiz.ui.views.QuestionView;

/**
 * Created by Leva on 28.01.2016.
 **/
public class QuestionPresenterImpl implements QuestionPresenter {

    private GameManager mGameManager;

    private QuestionView questionView;

    private Question mCurrentQuestion;
    private Stopwatch mStopwatch;

    public QuestionPresenterImpl(QuestionView questionView){
        mStopwatch = new Stopwatch();
        this.questionView = questionView;
        mGameManager = GameManager.getInstance(App.getContext());
    }

    @Override
    public void takeQuestion() {
        mCurrentQuestion = mGameManager.getQuestion();
        questionView.setQuestion(mCurrentQuestion);
        if (mCurrentQuestion != null) {
            OrmDao.getInstance(App.getContext()).setQuestionShown(mCurrentQuestion);
            mStopwatch.start();
        }
    }

    @Override
    public void sendResult() {
        ApiHelper.getHelper(App.getContext()).userResult(mGameManager.getUser(),
                null, null); // TODO Response.Listener<String>
    }

    @Override
    public void onAnswerButtonClick(String answer, AnswerButton button) {

        if (mCurrentQuestion.isAnswer(answer)) {
            // SUCCESS !!
            mCurrentQuestion.setAnswered();
            long time = mStopwatch.stop();
            int score = mCurrentQuestion.calcScore(time);
            mGameManager.getUser().addResult(score, Question.PLUS_MILLIS);
            questionView.onTrueAnswer(time, score, Question.PLUS_MILLIS);
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
    public String getCurrentCountry() {
        return OrmDao.getInstance(App.getContext()).getCountryName(mCurrentQuestion.country_id);
    }
}
