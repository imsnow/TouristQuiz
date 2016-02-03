package ru.mishaignatov.touristquiz.presenters;

import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.game.Stopwatch;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.presenters.QuestionPresenter;
import ru.mishaignatov.touristquiz.server.ApiHelper;
import ru.mishaignatov.touristquiz.ui.views.AnswerButton;
import ru.mishaignatov.touristquiz.ui.QuestionView;

/**
 * Created by Leva on 28.01.2016.
 **/
public class QuestionPresenterImpl implements QuestionPresenter {

    private QuestionView questionView;

    private Question mCurrentQuestion;
    private Stopwatch mStopwatch;

    public QuestionPresenterImpl(QuestionView questionView){
        mStopwatch = new Stopwatch();
        this.questionView = questionView;
    }

    @Override
    public void takeQuestion() {
        mCurrentQuestion = GameManager.getInstance(App.getContext()).getQuestion();
        questionView.setQuestion(mCurrentQuestion);
        mStopwatch.start();
    }

    @Override
    public void sendResult() {
        ApiHelper.getHelper(App.getContext()).userResult(GameManager.getInstance(App.getContext()).getUser(),
                null, null); // TODO Response.Listener<String>
    }

    @Override
    public void onAnswerButtonClick(String answer, AnswerButton button) {

        if (mCurrentQuestion.isAnswer(answer)) {
            // SUCCESS !!
            mCurrentQuestion.setAnswered();
            long time = mStopwatch.stop();
            int score = mCurrentQuestion.calcScore(time);
            GameManager.getInstance(App.getContext()).getUser().addResult(score, Question.MILLIS);
            questionView.onTrueAnswer(time, score, Question.MILLIS);
        }
        else { // Try again, buddy
            if(mCurrentQuestion.minusAttempt())
                questionView.onFailAnswer(button);
            else { // You are LOOSER!
                takeQuestion();
                questionView.onTotalFailure();
            }
        }
            //questionView.onFailAnswer(button);
    }

    @Override
    public void onDestroy() {
        questionView = null;
    }
}
