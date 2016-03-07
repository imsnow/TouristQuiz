package ru.mishaignatov.touristquiz.ui.views;

import android.view.animation.Animation;
import android.widget.TextView;

import ru.mishaignatov.touristquiz.database.Question;

/**
 * Created by Leva on 23.01.2016.
 **/
public interface QuestionView extends Animation.AnimationListener {

    void onTrueAnswer(long timeInMills, int score, int millis);
    void onFailAnswer(TextView button);
    void onTotalFailure();
    void showSuccessDialog(long timeInMills, int score, int millis);
    void showErrorInQuestionDialog();
    void showDialogNextLevel();
    void startDeleteButton(int resource_id);
    void setQuestion(Question question);

    void onResultDialog();
}
