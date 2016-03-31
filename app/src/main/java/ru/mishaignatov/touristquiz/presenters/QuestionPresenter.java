package ru.mishaignatov.touristquiz.presenters;

import android.widget.TextView;

/**
 * Created by Leva on 28.01.2016.
 **/
public interface QuestionPresenter extends BasePresenter {

    void takeQuestion();
    void sendResult();
    void onAnswerButtonClick(String answer, TextView button);
    String getCurrentLevel();
    void setQuestionNotShown();
}
