package ru.mishaignatov.touristquiz.ui;

import android.view.animation.Animation;

import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 23.01.2016.
 **/
public interface QuestionView extends Animation.AnimationListener {

    void onTrueAnswer();
    void onFailAnswer(AnswerButton button);
    void showSuccessDialog();
    void showErrorInQuestionDialog();
    void showDialogNextLevel();
    void startDeleteButton(int resource_id);
    void setQuestion(Question question);
}
