package ru.mishaignatov.touristquiz.ui;

import ru.mishaignatov.touristquiz.orm.Question;

/**
 * Created by Leva on 23.01.2016.
 **/
public interface QuestionView {

    boolean userAnswered(Question question, String answer);
    void updateQuestion();
}
