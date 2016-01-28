package ru.mishaignatov.touristquiz;

import ru.mishaignatov.touristquiz.game.App;
import ru.mishaignatov.touristquiz.game.GameManager;
import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.ui.AnswerButton;
import ru.mishaignatov.touristquiz.ui.QuestionView;

/**
 * Created by Leva on 28.01.2016.
 **/
public class QuestionPresenterImpl implements QuestionPresenter {

    private QuestionView questionView;

    public QuestionPresenterImpl(QuestionView questionView){
        this.questionView = questionView;
    }

    @Override
    public void takeQuestion() {
        Question q = GameManager.getInstance(App.getContext()).getQuestion();
    }

    @Override
    public void onAnswerButtonClick(String answer, AnswerButton button) {

        if (true)
            questionView.onTrueAnswer();
        else
            questionView.onFailAnswer(button);
    }

    @Override
    public void onDestroy() {
        questionView = null;
    }
}
