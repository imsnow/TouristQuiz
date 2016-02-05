package ru.mishaignatov.touristquiz.presenters;

import ru.mishaignatov.touristquiz.ui.views.AnswerButton;

/**
 * Created by Leva on 28.01.2016.
 **/
public interface QuestionPresenter {

    void takeQuestion();
    void sendResult();
    void onAnswerButtonClick(String answer, AnswerButton button);
    void onDestroy();
    String getCurrentCountry();
}
