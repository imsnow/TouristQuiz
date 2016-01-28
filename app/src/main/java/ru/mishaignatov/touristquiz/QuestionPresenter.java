package ru.mishaignatov.touristquiz;

import ru.mishaignatov.touristquiz.ui.AnswerButton;

/**
 * Created by Leva on 28.01.2016.
 **/
public interface QuestionPresenter {

    void takeQuestion();
    void onAnswerButtonClick(String answer, AnswerButton button);
    void onDestroy();
}
