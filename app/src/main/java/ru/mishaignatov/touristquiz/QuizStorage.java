package ru.mishaignatov.touristquiz;

import java.util.ArrayList;

/**
 * Created by Ignatov Misha on 02.08.15.
 */
public class QuizStorage {

    private static QuizStorage storage = null;
    private ArrayList<Quiz> listQuiz = new ArrayList<>();

    private QuizStorage() {

    }

    public QuizStorage getStorage(){
        if(storage == null) storage = new QuizStorage();
        return storage;
    }

    public void addQuiz(Quiz quiz){
        listQuiz.add(quiz);
    }
}
