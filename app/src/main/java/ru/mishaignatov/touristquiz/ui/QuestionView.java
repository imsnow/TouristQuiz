package ru.mishaignatov.touristquiz.ui;

import android.view.animation.Animation;

import ru.mishaignatov.touristquiz.orm.Question;
import ru.mishaignatov.touristquiz.ui.views.AnswerButton;

/**
 * Created by Leva on 23.01.2016.
 **/
public interface QuestionView extends Animation.AnimationListener {

    void onTrueAnswer(long timeInMills, int score, int millis);
    void onFailAnswer(AnswerButton button);
    void onTotalFailure();
    void showSuccessDialog(long timeInMills, int score, int millis);
    void showErrorInQuestionDialog();
    void showDialogNextLevel();
    void startDeleteButton(int resource_id);
    void setQuestion(Question question);
}
